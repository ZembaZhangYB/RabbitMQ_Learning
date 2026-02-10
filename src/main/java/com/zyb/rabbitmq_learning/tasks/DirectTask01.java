package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.Channel;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class DirectTask01 {
    //交换机名称
    public static final  String EXCHANGE_NAME = "direct";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        // 创建交换机（可有可无，因为消费者已经创建了交换机）
        // channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String message = "哇啦哇啦";
        channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes("UTF-8"));
        System.out.println("生产者发出消息：" + message);
    }
}
