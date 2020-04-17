package com.abc.component.rabbitmq.Listener;

import cn.hutool.core.util.RandomUtil;
import com.abc.service.IUserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
        redisTemplate.boundHashOps("code").put(email, code);
        userService.sendCode(email,code);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                redisTemplate.boundHashOps("code").delete(email);
            }
        }, 5 * 60 * 1000);
    }
}
