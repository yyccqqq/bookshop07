package com.abc.pojo.vo;

import com.abc.pojo.Orders;
import com.abc.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersVo {

    private Orders orders;

    private User buyer;

    private User seller;

    private List<BookVo> bookVoList;
}
