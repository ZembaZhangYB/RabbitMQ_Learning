package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.Channel;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class Task04 {
    private static final String QUEUE_NAME = "single_publish_confirm_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.confirmSelect();

        String msg = "单个发布确认模式测试";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        /**
         * 标记位，标志着发布已被确认
         */
        boolean flag = channel.waitForConfirms();
        System.out.println("单个发布确认模式测试 消息已发送");
        if (flag) System.out.println("发布成功");
    }

}
