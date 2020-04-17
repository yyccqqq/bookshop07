package com.abc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.abc.mapper.UserMapper;
import com.abc.pojo.User;
import com.abc.pojo.vo.Result;
import com.abc.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailAccount account;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public User findUserByStudentId(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, username);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User checkStudentId(String studentId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, studentId);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User checkEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void register(User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public void sendCode(String email, String code) {
        String emailMsg = "<head>\n" +
                "    <base target=\"_blank\" />\n" +
                "    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n" +
                "    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n" +
                "    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n" +
                "    <style type=\"text/css\">\n" +
                "        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n" +
                "        td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}\n" +
                "        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n" +
                "        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n" +
                "        img{ border:0}\n" +
                "        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n" +
                "        blockquote{margin-right:0px}\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body tabindex=\"0\" role=\"listitem\">\n" +
                "<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n" +
                "                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n" +
                "                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n" +
                "                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>\n" +
                "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
                "                        您正在进行<span style=\"color: red\">找回密码</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px\">" + code + "</span>，以完成操作。\n" +
                "                    </strong>\n" +
                "                </div>\n" +
                "                <div style=\"margin-bottom:30px;\">\n" +
                "                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n" +
                "                        <p style=\"color:#747474;\">\n" +
                "                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
                "                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n" +
                "                        </p>\n" +
                "                    </small>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"width:700px;margin:0 auto;\">\n" +
                "                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "                    <p>此为系统邮件，请勿回复<br>\n" +
                "                        请保管好您的邮箱，避免账号被他人盗用\n" +
                "                    </p>\n" +
                "                    <p>校园二手书</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n";
        MailUtil.send(account, CollUtil.newArrayList(account.getFrom(), email), "忘记密码", emailMsg, true);
    }

    @Override
    public Result updatePassword(Map map) {
        String checkCode = (String) redisTemplate.boundHashOps("code").get(map.get("email"));
        if (map.get("code").equals(checkCode)) {
            LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
            String password = (String) map.get("password");
            wrapper.eq(User::getEmail, map.get("email")).set(User::getPassword, SecureUtil.md5(password));
            userMapper.update(null, wrapper);
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }

    @Override
    public User findUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void editPassword(User user) {
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, loginUser.getId()).set(User::getPassword, SecureUtil.md5(user.getPassword()));
        userMapper.update(null, wrapper);
    }
}
