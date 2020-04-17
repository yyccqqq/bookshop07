package com.abc.component.shiro;


import com.abc.pojo.User;
import com.abc.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyReleam extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权认证。。。。");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.findUserByStudentId(username);
        if(user == null){
            throw new UnknownAccountException("账号不存在");
        }else{
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
    }
}
