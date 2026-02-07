package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.*;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class Worker04 {
    private static final String QUEUE_NAME = "single_publish_confirm_queue";
    private static final String URL_NAME = "127.0.0.1";
    private static final String ADMIN = "guest";
    private static final String PWD = "guest";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(ADMIN);
        factory.setPassword(PWD);
        factory.setHost(URL_NAME);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("已接收到消息" + consumerTag + new String(message.getBody()));
        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("未接收到消息" + consumerTag);
        };

        channel.basicConsume(QUEUE_NAME, deliverCallback, cancelCallback);
    }
}
