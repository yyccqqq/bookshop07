package com.abc.pojo.vo;

import com.abc.pojo.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVo {

   private Cart cart;

   private BookVo bookVo;

}
