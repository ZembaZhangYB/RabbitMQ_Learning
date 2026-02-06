package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.Channel;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class Task01 {
    public static final String QUEUE_NAME = "hello";
    private static final String URL_NAME = "127.0.0.1";
    private static final String ADMIN = "guest";
    private static final String PWD = "guest";

    public static void main(String[] args) throws Exception {
        String message = "Task01 开始发送消息";
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("Task01 已经发送信息");
    }
}
