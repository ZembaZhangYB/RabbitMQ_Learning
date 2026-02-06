package com.zyb.rabbitmq_learning.members;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    private static final String QUEUE_NAME = "hello";
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
        channel.queueDeclare("QUEUE_NAME", false, false, false, null);

        /**
         * 实现接收消息的回调接口
         */
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(new String(message.getBody()));
            }
        };
        /**
         * 也可以使用lambda表达式的形式：
         * DeliverCallback deliverCallback = (consumerTag, message) ->  {
         *                 System.out.println(new String(message.getBody()));
         *         };
         */

        /**
         * 实现取消消息的回调接口
         */
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                System.out.println("消息接收时被中断");
            }
        };
        /**
         * 或者使用lambda表达式的办法
         * CancelCallback cancelCallback = (s) -> {
         *                 System.out.println("消息接收时被中断");
         *         };
         */

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        /**
         * 参数概念
         * @param 1 ：接收消息的队列名称
         * @param 2 ：消费之后是否要自动应答
         * @param 3 ：成功消费的回调接口
         * @param 4 ：消费失败的回调接口
         */
    }
}