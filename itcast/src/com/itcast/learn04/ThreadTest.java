package com.itcast.learn04;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        List<String> strings = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
             Thread thread= new Thread(new Runnable() {
                @Override
                public void run() {
//                    System.out.println(Thread.currentThread().getName());
                    strings.add(UUID.randomUUID().toString());
                }
            });
             thread.start();
        }
        //阻塞等待线程执行完毕
//        while (strings.size()==10000);
        long end = System.currentTimeMillis();
        System.out.println("线程执行耗时" + (end - start) + "毫秒");
        System.out.println(strings.size());
    }
}
