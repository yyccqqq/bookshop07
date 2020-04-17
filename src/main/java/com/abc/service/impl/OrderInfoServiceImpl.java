package com.abc.service.impl;

import com.abc.mapper.OrderInfoMapper;
import com.abc.pojo.OrderInfo;
import com.abc.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-09
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
