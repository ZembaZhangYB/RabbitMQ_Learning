package com.zyb.rabbitmq_learning.Util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {
    private static final String URL = "127.0.0.1";
    private static final String ADMIN = "guest";
    private static final String PWD = "guest";

    public static Channel getChannel() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(URL);
        factory.setUsername(ADMIN);
        factory.setPassword(PWD);;

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;

    }
}
