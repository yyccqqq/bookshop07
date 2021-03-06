package com.abc.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.abc.mapper.*;
import com.abc.pojo.*;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.OrdersVo;
import com.abc.pojo.vo.PayLog;
import com.abc.service.IOrdersService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void submitOrder() {
        Set<Integer> set = new HashSet<>();
        List<Book> bookList = new ArrayList<>();
        Double totlaPrice = 10.0;
        List<Integer> ids = new ArrayList<>();
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        String idStr = (String) redisTemplate.boundHashOps("cartList").get(Convert.toStr(user.getId()));
        List<Integer> list = JSONUtil.toList(JSONUtil.parseArray(idStr), Integer.class);
        for (Integer cartId : list) {
            Cart cart = cartMapper.selectById(cartId);
            Book book = bookMapper.selectById(cart.getBookId());
            ids.add(book.getId());
            totlaPrice += book.getPrice();
            bookList.add(book);
            set.add(book.getUid());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("type", 3);
        rabbitTemplate.convertAndSend("removeExchange", "topic.update", map);

        List<String> orderIdList = new ArrayList<>();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        for (Integer userId : set) {
            long id = snowflake.nextId();
            orderIdList.add(Convert.toStr(id));
            for (Book book : bookList) {
                if (userId == book.getUid()) {
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrderId(Convert.toStr(id));
                    orderInfo.setBookId(book.getId());
                    orderInfoMapper.insert(orderInfo);
                }
            }
            Orders orders = new Orders();
            orders.setOrderId(Convert.toStr(id));
            orders.setTotalPrice(totlaPrice);
            orders.setBuyerId(user.getId());
            orders.setSellerId(userId);
            ordersMapper.insert(orders);
        }

        cartMapper.deleteBatchIds(list);
        redisTemplate.boundHashOps("cartList").delete(Convert.toStr(user.getId()));

        PayLog payLog = new PayLog(Convert.toStr(snowflake.nextId()), totlaPrice, orderIdList);
        String payLogStr = JSONUtil.toJsonStr(payLog);
        redisTemplate.boundHashOps("payLog").put(user.getId().toString(), payLogStr);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("orderIdList",orderIdList);
        rabbitTemplate.convertAndSend("OrderDelayExchange", "OrderDelay", map2, message -> {
            message.getMessageProperties().setExpiration(1000 * 60 *15 + "");
            return message;
        });
    }

    @Override
    public Map<String, String> getPayUrl() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String mapStr = (String) redisTemplate.boundHashOps("payUrlList").get(Convert.toStr(user.getId()));
        Map<String, String> map2 = JSONUtil.toBean(mapStr, Map.class);
        if (MapUtil.isNotEmpty(map2)) {
            return map2;
        } else {
            String payLogStr = (String) redisTemplate.boundHashOps("payLog").get(user.getId().toString());
            if (!StrUtil.hasEmpty(payLogStr)) {
                PayLog payLog = JSONUtil.toBean(payLogStr, PayLog.class);
                Map map = new HashMap();
                // 创建API对应的request类
                AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
                request.setBizContent("{" + "    \"out_trade_no\":\"" + payLog.getOrderId() + "\"," + // 商户订单号
                        "    \"total_amount\":\"" + Convert.toStr(payLog.getTotalPrice()) + "\","
                        + "    \"subject\":\"用户付款\"," + "    \"store_id\":\"001\","
                        + "    \"timeout_express\":\"90m\"}");// 订单允许的最晚付款时间
                try {
                    AlipayTradePrecreateResponse response = alipayClient.execute(request);
                    if (response.getCode().equals("10000")) {
                        map.put("orderNo", payLog.getOrderId());
                        map.put("money", Convert.toStr(payLog.getTotalPrice()));
                        map.put("qrCode", response.getQrCode());
                        redisTemplate.boundHashOps("payUrlList").put(Convert.toStr(user.getId()),
                                JSONUtil.toJsonStr(map));
                    }
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }
                return map;
            } else {
                return null;
            }
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        Map map = new HashMap();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();// 创建API对应的request类
        request.setBizContent("{" + "\"out_trade_no\":\"" + orderNo + "\"" + "}"); // 设置业务参数
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);// 通过alipayClient调用API，获得对应的response类
            if (response.isSuccess()) {
                map.put("orderStatus", response.getTradeStatus());
                return map;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void updatePayStatus(String orderNo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String payLogStr = (String) redisTemplate.boundHashOps("payLog").get(user.getId().toString());
        PayLog payLog = JSONUtil.toBean(payLogStr, PayLog.class);
        if (StrUtil.isNotEmpty(payLogStr)) {
            for (String orderId : payLog.getOrderList()) {
                LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
                wrapper.eq(Orders::getOrderId, orderId).set(Orders::getType, 1);
                ordersMapper.update(null, wrapper);
            }
        }
    }

    @Override
    public void clearPayLog() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        redisTemplate.boundHashOps("payLog").delete(user.getId().toString());
        redisTemplate.boundHashOps("payUrlList").delete(Convert.toStr(user.getId()));
    }

    @Override
    public PageInfo<OrdersVo> findOrderSend(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getBuyerId, user.getId()).eq(Orders::getType, 1).or().eq(Orders::getType, 2);
        PageInfo<OrdersVo> ordersVoPageInfo = getOrdersVoPageInfo(pageNum, wrapper);
        return ordersVoPageInfo;
    }

    @Override
    public void confirm(String orderId) {
        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Orders::getOrderId, orderId).set(Orders::getType, 3);
        ordersMapper.update(null, wrapper);
    }

    @Override
    public PageInfo<OrdersVo> findOrderPay(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getBuyerId, user.getId()).eq(Orders::getType, 0);
        PageInfo<OrdersVo> ordersVoPageInfo = getOrdersVoPageInfo(pageNum, wrapper);
        return ordersVoPageInfo;
    }

    @Override
    public PageInfo<OrdersVo> findOrderSell(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getSellerId, user.getId());
        PageInfo<OrdersVo> ordersVoPageInfo = getOrdersVoPageInfo(pageNum, wrapper);
        return ordersVoPageInfo;
    }

    @Override
    public PageInfo<OrdersVo> findOrderSuccess(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getBuyerId, user.getId()).eq(Orders::getType, 3).or().eq(Orders::getType, 4);
        PageInfo<OrdersVo> ordersVoPageInfo = getOrdersVoPageInfo(pageNum, wrapper);
        return ordersVoPageInfo;
    }

    @Override
    public void cancelOrder(String orderId) {
        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Orders::getOrderId, orderId).set(Orders::getType, 4);
        ordersMapper.update(null, wrapper);

        LambdaQueryWrapper<OrderInfo> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(OrderInfo::getOrderId, orderId);
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(wrapper2);
        List<Integer> ids = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            ids.add(orderInfo.getBookId());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("type", 1);
        rabbitTemplate.convertAndSend("addExchange", "topic.update", map);
    }

    @Override
    public void payAgain(String orderId) {
        List<String> orderIdList = new ArrayList<>();
        orderIdList.add(orderId);
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        Orders orders = ordersMapper.selectById(orderId);
        PayLog payLog = new PayLog(Convert.toStr(snowflake.nextId()), orders.getTotalPrice(), orderIdList);
        String payLogStr = JSONUtil.toJsonStr(payLog);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        redisTemplate.boundHashOps("payLog").put(user.getId().toString(), payLogStr);
    }

    @Override
    public Map<String, Object> getOrderDetail(String orderId) {
        Map<String, Object> map = new HashMap<>();
        Orders order = ordersMapper.selectById(orderId);
        User buyer = userMapper.selectById(order.getBuyerId());
        map.put("buyer", buyer);
        User seller = userMapper.selectById(order.getSellerId());
        map.put("seller", seller);
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOrderId, orderId);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectList(wrapper);
        List<BookVo> bookVoList = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfoList) {
            BookVo bookVo = getBookVo(orderInfo);
            bookVoList.add(bookVo);
        }
        map.put("bookVoList", bookVoList);
        map.put("order", order);
        return map;
    }

    private BookVo getBookVo(OrderInfo orderInfo) {
        Book book = bookMapper.selectById(orderInfo.getBookId());
        String booStr = JSONUtil.toJsonStr(book);
        BookVo bookVo = JSONUtil.toBean(booStr, BookVo.class);
        LambdaQueryWrapper<Bookimage> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Bookimage::getBookId, orderInfo.getBookId());
        Bookimage bookimage = bookimageMapper.selectOne(wrapper2);
        bookVo.setImage(bookimage.getImage());
        return bookVo;
    }

    private PageInfo<OrdersVo> getOrdersVoPageInfo(Integer pageNum, LambdaQueryWrapper<Orders> wrapper) {
        PageHelper.startPage(pageNum, 4);
        List<Orders> ordersList = ordersMapper.selectList(wrapper);
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        List<OrdersVo> ordersVoList = new ArrayList<>();
        for (Orders orders : pageInfo.getList()) {
            LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderInfo::getOrderId, orders.getOrderId());
            List<OrderInfo> orderInfos = orderInfoMapper.selectList(queryWrapper);
            List<BookVo> bookVoList = new ArrayList<>();
            for (OrderInfo orderInfo : orderInfos) {
                BookVo bookVo = getBookVo(orderInfo);
                bookVoList.add(bookVo);
            }
            User seller = userMapper.selectById(orders.getSellerId());
            User buyer = userMapper.selectById(orders.getBuyerId());
            OrdersVo ordersVo = new OrdersVo(orders, buyer, seller, bookVoList);
            ordersVoList.add(ordersVo);
        }
        PageInfo<OrdersVo> ordersVoPageInfo = new PageInfo<>();
        ordersVoPageInfo.setList(ordersVoList);
        ordersVoPageInfo.setTotal(pageInfo.getTotal());
        return ordersVoPageInfo;
    }

}
