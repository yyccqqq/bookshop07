package com.abc.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.abc.pojo.User;
import com.abc.pojo.vo.Result;
import com.abc.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(user.getStudentId(), SecureUtil.md5(user.getPassword())));
            return new Result(true, "登录成功");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result(false, "登录失败");
        }
    }

    @GetMapping("/findUser")
    public Map<String, String> findUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        HashMap<String, String> map = MapUtil.newHashMap();
        map.put("username", user.getName());
        map.put("id", Convert.toStr(user.getId()));
        return map;
    }

    @GetMapping("/checkStudentId/{studentId}")
    public Result checkStudentId(@PathVariable("studentId") String studentId) {
        User user = userService.checkStudentId(studentId);
        if (user == null) {
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }

    @GetMapping("/checkEmail/{email}")
    public Result checkEmail(@PathVariable String email) {
        User user = userService.checkEmail(email);
        if (user == null) {
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        try {
            userService.register(user);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/sendCode/{email}")
    public Result sendCode(@PathVariable String email) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            rabbitTemplate.convertAndSend("CodeDirectExchange", "CodeDirectRouting", map);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> map) {
        return userService.updatePassword(map);
    }

    @GetMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    @GetMapping("/findMy")
    public User findMy() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user = userService.findUserById(user.getId());
        return user;
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/findOldPassword/{oldPassword}")
    public Result findOldPassword(@PathVariable String oldPassword) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user.getPassword().equals(SecureUtil.md5(oldPassword))) {
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }

    @PutMapping("/editPassword")
    public Result editPassword(@RequestBody User user) {
        try {
            userService.editPassword(user);
            SecurityUtils.getSubject().logout();
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

}
