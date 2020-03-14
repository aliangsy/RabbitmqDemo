package com.example.redistest.rabbitmqtest;

import com.example.redistest.rabbitmqSend.MapUtile;
import com.example.redistest.rabbitmqSend.TwoTuple;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 测试向不同队列发送消息
 */
@Component
public class RabbitmqSend {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 设置一个map用于存放返回消息
     */
    private Map<Integer, TwoTuple> map = MapUtile.get();

    private Integer a  = 0;
    /**
     * 发送消息
     * @param bytes
     * @param rounkey
     * @param time
     */
    public void send(@Payload byte[] bytes, String rounkey, Long time){
        rabbitTemplate.convertAndSend("payorder.exchange", rounkey, bytes, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(time+"");
                return message;
            }
        });
    }

    @RabbitListener(bindings = @QueueBinding(value= @Queue(value="topic.business.test2",autoDelete="false"),
            exchange=@Exchange(value="payorder.exchange",type= ExchangeTypes.TOPIC),
            key="topic.business.#"))
    public void receiver(@Payload byte[] message) throws IOException {
        System.out.println("开启");
        String s = new String(message);
        a+=1;
        map.put(a,TwoTuple.tuple(a,s));

        while (true){
            if (map.get(a).second!=null){
                System.out.println("___________________");
                break;
            }
        }
        System.out.println(map.get(a).second);
//

    }
    @RabbitListener(bindings = @QueueBinding(value= @Queue(value="topic.business.test1",autoDelete="false"),
            exchange=@Exchange(value="payorder.exchange",type= ExchangeTypes.TOPIC),
            key="topic.business.test1"))
    public void receiver1(@Payload byte[] message) throws IOException {
        String s = new String(message);
        a+=1;
        map.put(a,TwoTuple.tuple(a,s));
        while (true){
            if (map.get(a).second!=null){
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            }
        }
        System.out.println(map.get(a).second);

    }

}
