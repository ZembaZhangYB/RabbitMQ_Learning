package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class FanoutWorker02 {
    private static final String EXCHANGE_NAME = "fanout";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        // 定义一个广播交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        // 进行交换机与队列的绑定，参数为队列名 交换机名 路由Key
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("FanoutWorker02等待接收消息，把接收到消息打印在屏幕上.....");

        DeliverCallback deliverCallback = (consumeTag, message) -> {
            System.out.println("收到消息：" + new String(message.getBody()));
        };

        CancelCallback cancelCallback = (consumeTag) -> {
            System.out.println(consumeTag + "消息未收到");
        };

        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
