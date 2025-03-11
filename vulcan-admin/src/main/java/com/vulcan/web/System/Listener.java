package com.vulcan.web.System;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.web.System
 * @name: Listener
 * @Date: 2024/4/16 下午8:26
 * @Description RabbitMQ消息监听器，用于接收和处理消息队列中的消息
 */
@Component
public class Listener {

    @RabbitListener(queues = "demoQueue")
    public void listenSimpleQueueMessage(String msg) {
        System.out.println("接收到的消息：" + msg);
    }

}
