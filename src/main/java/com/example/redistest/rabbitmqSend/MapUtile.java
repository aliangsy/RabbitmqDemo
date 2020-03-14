package com.example.redistest.rabbitmqSend;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置一个存储容器map
 */
public class MapUtile {

    private static  Map<Integer,TwoTuple> map = new HashMap<>();

    public static Map<Integer,TwoTuple> get(){
        return  map;
    }
}
