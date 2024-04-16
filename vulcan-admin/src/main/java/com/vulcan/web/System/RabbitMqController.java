package com.vulcan.web.System;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.web.System
 * @name: RabbitMqController
 * @Date: 2024/4/16 下午8:23
 * @Description //TODO
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送mq消息
     */
    @GetMapping("/sendMqMessage")
    public void sendMqMessage() {
        Message message = new Message("这是一条发送消息".getBytes());
        rabbitTemplate.send("demoQueue",message);
    }

}
