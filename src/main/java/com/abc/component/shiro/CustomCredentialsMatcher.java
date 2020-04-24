package com.abc.component.shiro;

import cn.hutool.crypto.SecureUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Object tokenCredentials = SecureUtil.md5(String.valueOf(usernamePasswordToken.getPassword()));
        Object accountCredentials = getCredentials(info);
        return equals(tokenCredentials, accountCredentials);
    }
}
