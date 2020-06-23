package com.abc.component.rabbitmq.Listener;

import cn.hutool.core.util.RandomUtil;
import com.abc.service.IUserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = "CodeDirectQueue")
public class CodeDirectReceiver {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IUserService userService;

    @RabbitHandler
    public void proess(Map message) {
        String code = RandomUtil.randomNumbers(6);
        String email = (String) message.get("email");
        redisTemplate.opsForValue().set(email, code,5L,TimeUnit.MINUTES);
        userService.sendCode(email,code);
    }
}
