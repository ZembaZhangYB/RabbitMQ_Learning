package com.zyb.rabbitmq_learning.members;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    private static final String QUEUE_NAME = "hello";
    private static final String URL_NAME = "127.0.0.1";
    private static final String ADMIN = "guest";
    private static final String PWD = "guest";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(URL_NAME);
        factory.setUsername(ADMIN);
        factory.setPassword(PWD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 参数的含义：
         * @param 1 ：队列名称
         * @param 2 ：消息是否要持久化到磁盘
         * @param 3 ：是否进行消息共享，消息是不是一对多个消费者
         * @param 4 ：队列关联的消费者都断开连接之后，这个队列是否自动删除
         * @param 5 ：其它参数，如队列/消息TTL，队列最大长度等
         */
        String message = "hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        /**
         * 参数的含义
         * @param 1 ：发送到哪个交换机
         * @param 2 ：发送到的队列的名称
         * @param 3 ：其它参数消息，如内容格式，内容编码算法等
         * @param 4 ：发送的消息
         */
        System.out.println("消息发送完毕");
    }
}
