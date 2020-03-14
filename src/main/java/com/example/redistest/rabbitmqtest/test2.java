package com.example.redistest.rabbitmqtest;

import com.example.redistest.dao.AddressBlockNumberMapper;
import com.example.redistest.domian.AddressNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * 项目启动执行方法
 */

@Component
@Order(value = 1)
public class test2 implements ApplicationRunner {
    @Autowired
    private AddressBlockNumberMapper addressBlockNumberMapper;
    /**
     * 开机就运行某个方法
     */

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<AddressNumber> addressNumbers = addressBlockNumberMapper.selectAllAddress();
        System.out.println(addressNumbers);
        for (AddressNumber s:addressNumbers
             ) {
            System.out.println(s);
        }

    }


}
