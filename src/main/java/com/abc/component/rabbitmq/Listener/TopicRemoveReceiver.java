package com.abc.component.rabbitmq.Listener;

import com.abc.component.elasticsearch.ESBookRepository;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "topic.remove")
public class TopicRemoveReceiver {

    @Autowired
    private ESBookRepository esBookRepository;

    @RabbitHandler
    public void proess(Map message) {
        List<Integer> ids= (List<Integer>) message.get("ids");
        for (Integer id : ids) {
            esBookRepository.deleteById(id);
        }

    }
}
