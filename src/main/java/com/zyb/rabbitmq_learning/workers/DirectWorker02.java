package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class DirectWorker02 {
    public static final  String EXCHANGE_NAME = "direct";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //声明一个队列
        channel.queueDeclare("disk",false,false,false,null);
        //交换机与队列进行绑定
        channel.queueBind("disk",EXCHANGE_NAME,"error");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("ReceiveLogsDirect01控制台打印接收到的消息：" + new String(message.getBody(),"UTF-8"));
        };


        CancelCallback cancelCallback = consumerTag -> {

        };


        channel.basicConsume("disk",true,deliverCallback,cancelCallback);


    }
}
