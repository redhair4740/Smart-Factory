package com.vulcan.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.mq.listener
 * @name: DemoQueueListener
 * @Date: 2024/4/16 下午8:30
 * @Description 示例队列监听器
 */
@Component
public class DemoQueueListener {

    @RabbitListener(queues = "demoQueue")
    public void receiveMessage(String message) {
        // 处理接收到的消息
        System.out.println("Received message: " + message);
    }
}