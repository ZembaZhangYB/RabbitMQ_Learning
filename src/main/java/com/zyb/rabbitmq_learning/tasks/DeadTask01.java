package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class DeadTask01 {
    public static final String NORMAL_EXCHANGE1 = "normal_exchange";


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();

        //死信消息 设置TTL时间  单位是ms  1000ms = 10s
        AMQP.BasicProperties properties =
                new AMQP.BasicProperties()
                        .builder().expiration("10000").build();
        channel.basicPublish(NORMAL_EXCHANGE1,"zhangsan",properties,"哇啦哇啦".getBytes());
    }
}

