package com.zyb.rabbitmq_learning.workers;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zyb.rabbitmq_learning.Util.RabbitMQUtils;

import java.util.HashMap;
import java.util.Map;

public class DeadWorker03 {
    //普通交换机及普通队列
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";

    //死信交换机及死信队列
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        //声明死信和普通交换机   类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");

        //声明普通队列
        //设置参数，将普通消息超时转为死信消息
        Map<String, Object> arguments = new HashMap<>();
        /*设置过期时间  单位：ms
          ---在普通队列到死信交换机时可以设置过期时间；
         在生产者发送消息的时候可以设置过期时间（常用），因为在生产者处设置的话，灵活性要高于在消费者中设置*/
        //arguments.put("x-message-ttl",10000);

        //普通队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-max-length", 6);

        //设置死信RoutingKEY  死信交换机与死信队列进行关联
        arguments.put("x-dead-letter-routing-key", "lisi");

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        //绑定普通交换机与普通队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        //绑定死信交换机与死信队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        System.out.println("等待接收消息.....");

        //接收消息
        //消息消费时的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println( "c1接收的消息是："+ new String(message.getBody(),"UTF-8"));
        };

        //消息取消时的回调函数
        CancelCallback cancelCallback = consumerTag -> {

        };

        //开启手动应答
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, cancelCallback);
    }
}
