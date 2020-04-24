package com.abc.service;

import com.abc.pojo.Orders;
import com.abc.pojo.vo.OrdersVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface IOrdersService extends IService<Orders> {

    void submitOrder();

    Map<String, String> getPayUrl();

    Map<String, String> queryPayStatus(String orderNo);

    void updatePayStatus(String orderNo);

    void clearPayLog();

    PageInfo<OrdersVo> findOrderSend(Integer pageNum);

    void confirm(String orderId);

    PageInfo<OrdersVo> findOrderPay(Integer pageNum);

    PageInfo<OrdersVo> findOrderSell(Integer pageNum);

    PageInfo<OrdersVo> findOrderSuccess(Integer pageNum);

    void cancelOrder(String orderId);

    void payAgain(String orderId);

    Map<String, Object> getOrderDetail(String orderId);
}
