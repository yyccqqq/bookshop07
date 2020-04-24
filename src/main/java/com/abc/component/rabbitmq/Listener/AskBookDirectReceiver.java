package com.abc.component.rabbitmq.Listener;

import com.abc.service.IAskbookService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "AskBookQueue")
public class AskBookDirectReceiver {
    @Autowired
    private IAskbookService askbookService;

    @RabbitHandler
    public void proess(Map message) {
        Integer bookId = (Integer) message.get("bookId");
        askbookService.createHtml(bookId);
    }
}
