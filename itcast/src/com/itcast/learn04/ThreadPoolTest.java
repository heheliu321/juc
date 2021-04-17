package com.itcast.learn04;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 线程执行耗时987毫秒
 100000
 */

public class ThreadPoolTest {

    private static CountDownLatch latch = new CountDownLatch(100000);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        List<String> strings = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    strings.add(UUID.randomUUID().toString());
                    latch.countDown();
                }
            });
        }
        //阻塞等待线程执行完毕
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("线程执行耗时" + (end - start) + "毫秒");
        System.out.println(strings.size());
    }
}
