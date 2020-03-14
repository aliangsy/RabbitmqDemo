package com.example.redistest.rabbitmqtest;

import com.example.redistest.rabbitmqSend.MapUtile;
import com.example.redistest.rabbitmqSend.TwoTuple;

import java.util.Map;

public class test {
    private static  boolean isOver = false;
    private Integer a = 0;

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis()+"开始");
                while (!isOver) {
                    if (MapUtile.get()!=null&&null!=MapUtile.get().get(2)) {
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        break;
                    }
//                    System.out.println("~~~~~~~~~~~~~~~~~~~~");
                }
            }
        });
        thread.start();
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()+"结束");
//        isOver = true;
//        isOver = false;
        new Thread(()->{
            System.out.println(System.currentTimeMillis()+"测试开始");
            int a = 0;
            a+=1;
            for(int b = 0;b<10;b++) {
                Map<Integer, TwoTuple> integerTwoTupleMap = MapUtile.get();
                integerTwoTupleMap.put(b, TwoTuple.tuple(b, "成功"));
                System.out.println(integerTwoTupleMap.get(b).first);
            }
        }).start();
    }
}
