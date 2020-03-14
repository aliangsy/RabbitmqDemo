//package com.example.redistest.rabbitmqSend;
//
//
//import com.rabbitmq.client.Channel;
//import org.apache.commons.pool2.PooledObject;
//import org.apache.commons.pool2.PooledObjectFactory;
//import org.apache.commons.pool2.impl.DefaultPooledObject;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @Author liang
// * @Date 2020/3/13
// * @Description rabbitmq连接池工厂
// **/
//
//public class RabbitMQChannelPoolFactory implements PooledObjectFactory<Channel> {
//    private RabbitmqContionFactory factory;
//
//    public RabbitMQChannelPoolFactory(ConnectionFactory factory) {
//        this.factory = (RabbitmqContionFactory) factory;
//    }
//
//    @Override
//    public PooledObject<Channel> makeObject() throws Exception {
//        System.out.println("~~~~~~~~~~~~~~~");
//        // 池对象创建实例化资源
//        return new DefaultPooledObject<Channel>(factory.newConnection().createChannel());
//    }
//
//    @Override
//    public void destroyObject(PooledObject<Channel> p) throws Exception {
//        // 池对象销毁资源
//        if (p != null && p.getObject() != null && p.getObject().isOpen()) {
//            p.getObject().close();
//        }
//    }
//
//    @Override
//    public boolean validateObject(PooledObject<Channel> p) {
//        // 验证资源是否可用
//        return p.getObject() != null && p.getObject().isOpen();
//    }
//
//    @Override
//    public void activateObject(PooledObject<Channel> p) throws Exception {
//
//    }
//
//    @Override
//    public void passivateObject(PooledObject<Channel> p) throws Exception {
//
//    }
//}