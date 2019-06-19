package com.itcast.learn02;

public class MyTask implements  Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "我在执行下载文件耗时操作");
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + "sleep....");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}