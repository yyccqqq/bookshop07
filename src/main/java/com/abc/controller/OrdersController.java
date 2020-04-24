package com.abc.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.abc.pojo.vo.OrdersVo;
import com.abc.pojo.vo.Result;
import com.abc.service.IOrdersService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @GetMapping("/submitOrder")
    public Result submitOrder() {
        try {
            ordersService.submitOrder();
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/getPayUrl")
    public Map<String, String> getPayUrl() {
        return ordersService.getPayUrl();
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        int count = 0;
        Map<String, String> map = new HashMap<>();
        while (true) {
            map = ordersService.queryPayStatus(orderNo);
            if (MapUtil.isNotEmpty(map)) {
                String status = map.get("orderStatus");
                if (!StrUtil.hasEmpty(status)) {
                    if (status.equals("TRADE_SUCCESS")) {
                        ordersService.updatePayStatus(orderNo);
                        ordersService.clearPayLog();
                        return new Result(true, "付款成功");
                    } else if (status.equals("TRADE_CLOSED")) {
                        ordersService.clearPayLog();
                        return new Result(false, "交易关闭");
                    } else if (status.equals("TRADE_FINISHED")) {
                        ordersService.clearPayLog();
                        return new Result(false, "交易结束");
                    }
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            if (count == 20) {
                ordersService.clearPayLog();
                return new Result(false, "支付超时");
            }
        }
    }

    @GetMapping("/findOrderSend/{pageNum}")
    public PageInfo<OrdersVo> findOrderSend(@PathVariable Integer pageNum) {
        return ordersService.findOrderSend(pageNum);
    }

    @GetMapping("/confirm/{orderId}")
    public Result confirm(@PathVariable String orderId) {
        try {
            ordersService.confirm(orderId);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/findOrderPay/{pageNum}")
    public PageInfo<OrdersVo> findOrderPay(@PathVariable Integer pageNum) {
        return ordersService.findOrderPay(pageNum);
    }

    @GetMapping("/findOrderSell/{pageNum}")
    public PageInfo<OrdersVo> findOrderSell(@PathVariable Integer pageNum) {
        return ordersService.findOrderSell(pageNum);
    }

    @GetMapping("/findOrderSuccess/{pageNum}")
    public PageInfo<OrdersVo> findOrderSuccess(@PathVariable Integer pageNum) {
        return ordersService.findOrderSuccess(pageNum);
    }

    @GetMapping("/cancelOrder/{orderId}")
    public Result cancelOrder(@PathVariable String orderId) {
        try {
            ordersService.cancelOrder(orderId);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/payAgain/{orderId}")
    public Result payAgain(@PathVariable String orderId) {
        try {
            ordersService.payAgain(orderId);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/getOrderDetail/{orderId}")
    public Map<String,Object> getOrderDetail(@PathVariable String orderId){
        return ordersService.getOrderDetail(orderId);
    }


}
