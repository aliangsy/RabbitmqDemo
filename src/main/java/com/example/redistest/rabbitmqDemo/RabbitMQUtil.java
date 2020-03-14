package com.example.redistest.rabbitmqDemo;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import sun.plugin2.util.PojoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@PropertySource("classpath:config.properties")
public class RabbitMQUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQUtil.class);
    private static final ConnectionFactory FACTORY = new ConnectionFactory() ;
    private static final List<Connection> CONNECTIONS = new ArrayList<Connection>();
    private static final int MAX_CONNECTION = 20;

    /**
     * MQ的host地址
     */
    @Value("${mq.host}")
    private String host;


    /**
     * MQ的端口
     */
    @Value("${mq.port}")
    private int port;


    static {
        initFactory("192.168.0.154",5672);
    }
    /**
     * 向指定的消息队列发送消息
     *
     * @param message 消息体
     * @param queue   队列名
     */
    public static void sendMessage(String message, String queue) {
        try {
            // 从队列中获取一个连接
            Connection connection = getConnection();
            // 创建一个MQ的管道
            Channel channel = connection.createChannel();
            // 将管道绑定到一个队列上
            channel.queueDeclare(queue, false, false, false, null);
            // 向指定的队列发送消息
            channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
            // 关闭管道
            channel.close();
            // 将连接放回到队列中
            setConnection(connection);
        } catch (Exception e) {
            throw new RuntimeException("RabbitMQ connection fail, send message fail!", e);
        }
    }


    /**
     * 向指定的消息队列取出固定数量的数据
     *
     * @param queue 消息队列名
     * @param count 取出的消息数量
     * @return
     */
    public static List<String> receiveMessages(String queue, int count) {
        List<String> list = new ArrayList<String>();
        try {
            // 从队列中获取连接
            Connection connection = getConnection();
            // 创建一个管道
            Channel channel = connection.createChannel();
            // 将管道绑定到队列上
            channel.queueDeclare(queue, false, false, false, null);

            boolean autoAck = false;
            channel.basicConsume(queue, autoAck, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, @Payload byte[] body) throws IOException {

                    //这里将值获取到~~~~~~~~~~~~~~~~~~~~~~~~~
                }
            });
            // 关闭管道
            channel.close();
            // 将连接放回到队列中
            setConnection(connection);
        } catch (Exception e) {
            LOGGER.error("RabbitMQ connection fail, receive message fail!", e);
        } finally {
            return list;
        }
    }

    /**
     * 从队列中获取连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return getAndSetConnection(true, null);
        } catch (Exception e) {
            throw new RuntimeException("connection MQ fail", e);
        }
    }

    /**
     * 将使用完毕的连接放回到队列中
     *
     * @param connection
     */
    private static void setConnection(Connection connection) {
        try {
            getAndSetConnection(false, connection);
        } catch (Exception e) {
            throw new RuntimeException("connection MQ fail", e);
        }
    }

    /**
     * 通过同步锁控制连接队列，根据参数isGet来区分本次调用是从队列中取连接还是存放连接
     * @param isGet      取出或者放回的标记，true表示取连接，false表示放回连接
     * @param connection 取连接：null, 放回连接：具体连接对象
     * @return 取连接时，返回具体连接对象，放回连接时，返回null
     * @throws Exception
     */
    private static synchronized Connection getAndSetConnection(boolean isGet, Connection connection) throws Exception {
        if (isGet) {
            // 取连接，如果队列中不存在连接，则新建一个连接
            if (CONNECTIONS.isEmpty()) {
                return FACTORY.newConnection();
            }
            Connection newConnection = CONNECTIONS.get(0);

            CONNECTIONS.remove(0);
            if (newConnection.isOpen()) {
                return newConnection;
            } else {
                return FACTORY.newConnection();
            }
        } else {
            // 放回连接，如果队列中的连接数超过了MAX_CONNECTION指定数量的连接，则抛弃该连接
            if (CONNECTIONS.size() < MAX_CONNECTION) {
                CONNECTIONS.add(connection);
            }
            return null;
        }
    }

    public static void initFactory(String host, int port) {
        FACTORY.setHost(host);
        FACTORY.setPort(port);
        FACTORY.setUsername("guest");
        FACTORY.setPassword("guest");
    }

    public static void initFactory2(String host, int port,String name,String password) {
        System.out.println("mq登录成功");
        FACTORY.setHost(host);
        FACTORY.setPort(port);
        FACTORY.setUsername(name);
        FACTORY.setPassword(password);
    }
}

