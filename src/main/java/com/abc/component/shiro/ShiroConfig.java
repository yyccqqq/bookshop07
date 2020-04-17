package com.abc.component.shiro;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class ShiroConfig {

    @Bean
    public MyReleam myReleam() {
        return new MyReleam();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myReleam());
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/css/**", "anon");
        definition.addPathDefinition("/img/**", "anon");
        definition.addPathDefinition("/js/**", "anon");
        definition.addPathDefinition("/plugin/**", "anon");
        definition.addPathDefinition("/login.html", "anon");
        definition.addPathDefinition("/register.html", "anon");
        definition.addPathDefinition("/password.html", "anon");
        definition.addPathDefinition("/user/login", "anon");
        definition.addPathDefinition("/user/checkStudentId/**", "anon");
        definition.addPathDefinition("/user/checkEmail/**", "anon");
        definition.addPathDefinition("/user/register", "anon");
        definition.addPathDefinition("/user/sendCode/**", "anon");
        definition.addPathDefinition("/user/updatePassword", "anon");
        definition.addPathDefinition("/view/**", "authc");
        definition.addPathDefinition("/**", "authc");
        return definition;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/unauthor");
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "/login.html");
        resolver.setExceptionMappings(properties);
        return resolver;
    }

}