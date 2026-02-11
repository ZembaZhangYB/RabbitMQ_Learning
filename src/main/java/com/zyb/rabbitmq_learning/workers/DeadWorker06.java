package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class DeadWorker06 {
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        System.out.println("等待接收消息.....");

        //接收消息
        //消息消费时的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer02接收的消息是：" + new String(message.getBody(), "UTF-8"));
        };
        //消息取消时的回调函数
        CancelCallback cancelCallback = consumerTag -> {

        };
        // 仅消费死信队列的东西
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, cancelCallback);
    }
}
