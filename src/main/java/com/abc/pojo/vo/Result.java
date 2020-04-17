package com.abc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * company: www.abc.com
 * Author: Administrator
 * Create Data: 2019/11/20
 */
@Data
@AllArgsConstructor
public class Result {
    private boolean success;

    private String message;
}
