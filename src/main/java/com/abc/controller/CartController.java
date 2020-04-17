package com.abc.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.abc.pojo.Cart;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.CartResult;
import com.abc.pojo.vo.Result;
import com.abc.service.ICartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/findAll")
    public List<Cart> findAll() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return cartService.findAll(user.getId());
    }

    @GetMapping("/addCart/{bookId}")
    public Result addCart(@PathVariable Integer bookId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        try {
            cartService.addCart(user.getId(), bookId);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/findBookInCart")
    public List<CartResult> findBookInCart() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return cartService.findBookInCart(user.getId());
    }

    @DeleteMapping("/delete/{cartId}")
    public Result delete(@PathVariable Integer cartId) {
        try {
            cartService.delete(cartId);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @PostMapping("/checkBook")
    public List<String> checkBook(@RequestBody Integer[] cartIds) {
        return cartService.checkBook(cartIds);
    }

    @GetMapping("/getSelectCartList")
    public Map<String, Object> getSelectCartList() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String idStr = (String) redisTemplate.boundHashOps("cartList").get(Convert.toStr(user.getId()));
        List<Integer> list = JSONUtil.toList(JSONUtil.parseArray(idStr), Integer.class);
        Integer[] cartIds = Convert.toIntArray(list);
        if (cartIds.length > 0) {
            List<BookVo> bookVoList = cartService.getSelectCartList(cartIds);
            Map<String, Object> map = new HashMap<>();
            map.put("bookVoList", bookVoList);
            map.put("user", user);
            return map;
        } else {
            return null;
        }

    }

    @PostMapping("/addToCartList")
    public Result addToCartList(@RequestBody Integer[] ids) {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            String idStr = JSONUtil.toJsonStr(ids);
            redisTemplate.boundHashOps("cartList").put(Convert.toStr(user.getId()), idStr);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

}
