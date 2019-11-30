package com.portal.webapp.controller;

import com.portal.webapp.model.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rabbit")
public class RabbitController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test/{name}")
    public String testApi(@PathVariable("name") String name) {
        Person p = new Person(1l, name);
        rabbitTemplate.convertAndSend("Mobile", p);
        rabbitTemplate.convertAndSend("Direct-Exchange", "mobile", p);
        rabbitTemplate.convertAndSend("Fanout-Exchange", "", p);
        rabbitTemplate.convertAndSend("Topic-Exchange", "tv.mobile.ac", p);
        return "Success";
    }

}
