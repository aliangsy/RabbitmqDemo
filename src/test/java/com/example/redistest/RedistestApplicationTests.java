package com.example.redistest;

import com.example.redistest.dao.AddressBlockNumberMapper;
import com.example.redistest.domian.AddressNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RedistestApplicationTests {
    @Autowired
    private AddressBlockNumberMapper addressBlockNumberMapper;
    @Test
    void contextLoads() {
        List<AddressNumber> addressNumbers = addressBlockNumberMapper.selectAllAddress();
        System.out.println(addressNumbers);
    }

}
