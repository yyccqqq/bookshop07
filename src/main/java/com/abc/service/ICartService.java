package com.abc.service;

import com.abc.pojo.Cart;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.CartResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface ICartService extends IService<Cart> {



    List<Cart> findAll(Integer uid);

    void addCart(Integer uid, Integer bookId);

    List<CartResult> findBookInCart(Integer uid);

    void delete(Integer cartId);

    List<String> checkBook(Integer[] cartIds);

    List<BookVo> getSelectCartList(Integer[] cartIds);
}
