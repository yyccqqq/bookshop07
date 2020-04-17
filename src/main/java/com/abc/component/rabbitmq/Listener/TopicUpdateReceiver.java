package com.abc.component.rabbitmq.Listener;

import com.abc.service.IBookService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "topic.update")
public class TopicUpdateReceiver {

    @Autowired
    private IBookService bookService;

    @RabbitHandler
    public void proess(Map message) {
        List<Integer> ids= (List<Integer>) message.get("ids");
        Integer bookType = (Integer) message.get("type");
        for (Integer id : ids) {
            bookService.updateBookType(id,bookType);
        }
    }
}
