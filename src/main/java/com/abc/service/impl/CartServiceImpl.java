package com.abc.service.impl;

import cn.hutool.json.JSONUtil;
import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.mapper.CartMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.Cart;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.CartResult;
import com.abc.pojo.vo.CartVo;
import com.abc.service.ICartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Cart> findAll(Integer uid) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, uid);
        List<Cart> carts = cartMapper.selectList(wrapper);
        return carts;
    }

    @Override
    public void addCart(Integer uid, Integer bookId) {
        Cart cart = new Cart();
        cart.setBookId(bookId);
        cart.setUserId(uid);
        cartMapper.insert(cart);
    }

    @Override
    public List<CartResult> findBookInCart(Integer uid) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, uid);
        List<Cart> carts = cartMapper.selectList(wrapper);
        Set<Integer> set = new HashSet<>();
        for (Cart cart : carts) {

            Book book = bookMapper.selectById(cart.getBookId());
            set.add(book.getUid());
        }

        List<CartResult> cartResultList = new ArrayList<>();
        for (Integer userId : set) {
            User user = userMapper.selectById(userId);
            List<CartVo> cartVoList = new ArrayList<>();
            for (Cart cart : carts) {
                Book book = bookMapper.selectById(cart.getBookId());
                if (userId == book.getUid()) {
                    LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Bookimage::getBookId, book.getId());
                    Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
                    String bookStr = JSONUtil.toJsonStr(book);
                    BookVo bookVo = JSONUtil.toBean(bookStr, BookVo.class);
                    bookVo.setImage(bookimage.getImage());
                    CartVo cartVo = new CartVo(cart, bookVo);
                    cartVoList.add(cartVo);
                }
            }
            CartResult cartResult = new CartResult(user, cartVoList);
            cartResultList.add(cartResult);
        }
        return cartResultList;
    }

    @Override
    public void delete(Integer cartId) {
        cartMapper.deleteById(cartId);
    }

    @Override
    public List<String> checkBook(Integer[] cartIds) {
        List<String> list = new ArrayList<>();
        for (Integer cartId : cartIds) {
            Cart cart = cartMapper.selectById(cartId);
            Book book = bookMapper.selectById(cart.getBookId());
            if(book.getBookType() == 2){
                list.add(book.getName());
            }
        }
        return list;
    }

    @Override
    public List<BookVo> getSelectCartList(Integer[] cartIds) {
        List<BookVo> bookVoList = new ArrayList<>();
        for (Integer cartId : cartIds) {
            Cart cart = cartMapper.selectById(cartId);
            Book book = bookMapper.selectById(cart.getBookId());
            LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Bookimage::getBookId, book.getId());
            Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
            String bookStr = JSONUtil.toJsonStr(book);
            BookVo bookVo = JSONUtil.toBean(bookStr, BookVo.class);
            bookVo.setImage(bookimage.getImage());
            bookVoList.add(bookVo);
        }
        return bookVoList;
    }
}
