package com.abc.service;

import com.abc.pojo.User;
import com.abc.pojo.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface IUserService extends IService<User> {

    User findUserByStudentId(String username);

    User checkStudentId(String studentId);

    User checkEmail(String email);

    void register(User user);

    void sendCode(String email, String code);

    Result updatePassword(Map map);

    User findUserById(Integer userId);

    void updateUser(User user);

    void editPassword(User user);
}
