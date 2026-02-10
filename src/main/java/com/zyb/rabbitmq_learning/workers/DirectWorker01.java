package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

public class DirectWorker01 {
    public static final  String EXCHANGE_NAME = "direct";

    public static void main(String[] args) throws Exception {

        //声明信道
        Channel channel = RabbitMQUtils.getChannel();

        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");//当然，交换机类型也可以这样写"direct"

        //声明一个队列
        channel.queueDeclare("console",false,false,false,null);
        //交换机与队列进行绑定
        channel.queueBind("console",EXCHANGE_NAME,"info");
        channel.queueBind("console",EXCHANGE_NAME,"warning");

        //接收消息的回调接口
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("ReceiveLogsDirect01控制台打印接收到的消息：" + new String(message.getBody(),"UTF-8"));
        };


        //消费者取消消费时回调接口
        CancelCallback cancelCallback = consumerTag -> {

        };//此处不处理，仅为案例演示


        channel.basicConsume("console",true,deliverCallback,cancelCallback);


    }
}
