package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class DeadTask02 {
    public static final String NORMAL_EXCHANGE1 = "normal_exchange";


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            // channel.basicPublish(NORMAL_EXCHANGE1,"zhangsan",properties,message.getBytes());
            channel.basicPublish(NORMAL_EXCHANGE1, "zhangsan", null, message.getBytes());
        }

    }
}

