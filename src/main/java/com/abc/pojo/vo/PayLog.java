package com.abc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;

    private Double totalPrice;

    private List<String> orderList;
}
