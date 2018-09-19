package com.mlh.controller;

import com.mlh.config.RabbitMqConfig;
import com.mlh.mq.model.Book;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/19
 * @description:
 */
@RestController
@RequestMapping(value = "/books")
public class MqController {

    private final RabbitTemplate rabbitTemplate; //

    @Autowired
    public MqController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public void defaultMessage() {
        Book book = new Book();
        book.setId("1");
        book.setName("一起来学Spring Boot");
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.DEFAULT_BOOK_QUEUE, book);
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.MANUAL_BOOK_QUEUE, book);
    }
}
