package juc_day01.atguigu.sso.sync;

/**
 * @author n00444323
 * @date 2021/4/17 9:44
 *
 * todo 手写一个LRU算法
 *
 */

import java.util.concurrent.CountDownLatch;

public class Test1 {

    static CountDownLatch latch = new CountDownLatch(2);

    private volatile static int num = 1000;

    private static final Object object = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(new ThreadB(), "窗口1");
        Thread t2 = new Thread(new ThreadB(), "窗口2");

        t1.start();
        t2.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (object) {
                    if (num >= 1) {
                        System.out.println("剩余票数：" + --num + "-----" + Thread.currentThread().getName());
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
