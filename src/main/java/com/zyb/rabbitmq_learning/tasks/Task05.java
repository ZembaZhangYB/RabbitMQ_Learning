package com.zyb.rabbitmq_learning.tasks;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Task05 {
    private static final String QUEUE_NAME = "async";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.confirmSelect();
        /**
         * 准备一个支持高并发的容器，需要可以将序号和消息关联起来，方便异步的确认
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
        /**
         * 消息确认成功  回调函数，deliverTag是
         */
        ConfirmCallback ackCallback = (deliveryTag,multiple) ->{
            if (multiple) {//如果消息是批量的
                //2.删除掉已经确认的消息     剩下的就是未确认的消息
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
            }
            System.out.println("确认的消息：" + deliveryTag);
        };

        //消息确认失败  回调函数
        ConfirmCallback nackCallback = (deliveryTag,multiple) ->{
            // System.out.println("未确认的消息：" + deliveryTag);
            //3.打印一下未确认的消息都有哪些
            String message = outstandingConfirms.get(deliveryTag);
            System.out.println("未确认的消息是："+message +":::::未确认的消息tag:" + deliveryTag);
        };

        for (int i = 0; i < 10; i++) {
            String message = "消息" + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            //1.此处记录下所有要发送的消息：消息的总和
            outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
        }
    }
}