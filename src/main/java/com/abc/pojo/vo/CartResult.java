package com.abc.pojo.vo;

import com.abc.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResult {

    private User user;

    private List<CartVo> cartVoList;
}
