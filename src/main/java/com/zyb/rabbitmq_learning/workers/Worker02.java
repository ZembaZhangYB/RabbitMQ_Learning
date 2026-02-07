package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class Worker02 {
    private static final String QUEUE_NAME = "ack_queue";
    private static final String URL_NAME = "127.0.0.1";
    private static final String ADMIN = "guest";
    private static final String PWD = "guest";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Worker01 接收到了消息 " + new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            /**
             * @param 1 ：应该被确认的消息的编号
             * @param 2 ：是否要批量确认（若5,6,7,8都没确认，现在tag=8,那么true就是把前面一起确认了，false是只确认8号）
             */
        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "Worker01 接收消息失败");
        };

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
        /**
         * 这里的false是不使用自动应答
         */

    }
}
