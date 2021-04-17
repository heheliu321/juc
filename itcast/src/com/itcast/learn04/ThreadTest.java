package com.itcast.learn04;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 线程执行耗时10725毫秒
 99916
 */
public class ThreadTest {

    private static CountDownLatch latch = new CountDownLatch(100000);

    public static void main(String[] args) throws InterruptedException {
        List<String> strings = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    strings.add(UUID.randomUUID().toString());
                    latch.countDown();
                }
            });
            thread.start();
        }
        //阻塞等待线程执行完毕
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("线程执行耗时" + (end - start) + "毫秒");
        System.out.println(strings.size());
    }
}
