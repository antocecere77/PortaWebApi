package com.portal.webapp.queue;

import com.portal.webapp.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "Mobile")
    public void getMessage(Person p) {
        System.out.println(p.getName());
    }

}