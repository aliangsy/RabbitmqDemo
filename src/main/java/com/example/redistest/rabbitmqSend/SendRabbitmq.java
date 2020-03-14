//package com.example.redistest.rabbitmqSend;
//
//
//import com.rabbitmq.client.AMQP;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeoutException;
//
///**
// * mq发送消息
// */
//public class SendRabbitmq {
//
//    private RabbitTemplate rabbitTemplate;
//    private static final String ROUNKEY = "topic.pay.demo";
//    private static final String EXCHANGE = "payorder.exchange";
//    private static final String FINDTIME = "1000";
//    private RabbitMqChannelPool rabbitMqChannelPool;
//    public void send() throws IOException, TimeoutException {
//        Channel channel1 = rabbitMqChannelPool.getChannel();
//        System.out.println(channel1);
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("172.31.21.78");
//        connectionFactory.setPort(5672);
//        connectionFactory.setVirtualHost("/");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        RabbitmqContionFactory rabbitmqContionFactory = new RabbitmqContionFactory();
////        org.springframework.amqp.rabbit.connection.Connection set = rabbitmqContionFactory.set();
//
////        set.createChannel(true);
//        Connection connection = connectionFactory.newConnection();
//        Channel channel = connection.createChannel();
//        //设置消息过期时间
//        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
//        AMQP.BasicProperties properties = builder.expiration(FINDTIME).build();
//        //发送得消息
//        String message = " mq压力测试 ";
//
//        //生产者需要设置路由key和交换机
//        channel.basicPublish(EXCHANGE,ROUNKEY,properties,message.getBytes("UTf-8"));
//        //设置消息确认模式
//        channel.confirmSelect();
//    }
//
//}
