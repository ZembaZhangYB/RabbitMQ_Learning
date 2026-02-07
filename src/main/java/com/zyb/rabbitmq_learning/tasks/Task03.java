package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 发布确认 发布者 示例
 */
public class Task03 {
    private static final String QUEUE_NAME = "publish_confirm_queue";
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
        /**
         * 开启发布确认模式
         */
        channel.confirmSelect();

        String msg = "发布确认模式测试";
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("发布确认模式测试 消息已发送");
    }
}
