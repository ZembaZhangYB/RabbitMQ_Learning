package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.Channel;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class FanoutTask01 {
    private static final String EXCHANGE_NAME = "fanout";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        //创建交换机  --可创建也可以不用创建，因为消费者已经创建了
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        channel.basicPublish(EXCHANGE_NAME, "", null, "哇啦哇啦".getBytes());
    }

}
