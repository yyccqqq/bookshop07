package com.abc.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Bean
    public MailAccount mailAccount(){
        MailAccount account = new MailAccount();
        account.setHost("smtp.163.com");
        account.setAuth(true);
        account.setPort(25);
        account.setFrom("yanchuanqi234@163.com");
        account.setUser("yanchuanqi234@163.com");
        account.setPass("NLTYJLYNRDSTWUNQ");
        return account;
    }
}
