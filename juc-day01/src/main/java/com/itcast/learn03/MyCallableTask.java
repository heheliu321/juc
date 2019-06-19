package com.itcast.learn03;

import java.util.concurrent.Callable;

public class MyCallableTask implements Callable<String> {
    @Override
    public String call() throws Exception {

        System.out.println(Thread.currentThread().getName() + "我在执行下载文件耗时操作");
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + "sleep....");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}