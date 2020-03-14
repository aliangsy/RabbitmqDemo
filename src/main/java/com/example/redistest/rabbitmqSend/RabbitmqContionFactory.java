//package com.example.redistest.rabbitmqSend;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//@Component
//public class RabbitmqContionFactory {
////    @Bean
////    public org.springframework.amqp.rabbit.connection.Connection set() throws IOException, TimeoutException {
//////        ConnectionFactory connectionFactory = new ConnectionFactory();
////        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
////        cachingConnectionFactory.setHost("192.168.0.154");
////        cachingConnectionFactory.setPort(5672);
////        cachingConnectionFactory.setPassword("guest");
////        cachingConnectionFactory.setUsername("guest");
////        cachingConnectionFactory.setVirtualHost("/");
////        cachingConnectionFactory.setChannelCacheSize(50);
//////        Channel channel = cachingConnectionFactory.createConnection().createChannel(false);
////
////
////        return cachingConnectionFactory.createConnection();
////    }
//
//    public Connection newConnection() throws IOException, TimeoutException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        Connection connection = connectionFactory.newConnection();
//        return connection;
//    }
//
//}
