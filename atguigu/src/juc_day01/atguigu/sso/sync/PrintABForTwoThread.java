package juc_day01.atguigu.sso.sync;

/**
 * @author n00444323
 * @date 2021/4/17 9:44
 */

import java.util.concurrent.CountDownLatch;

public class PrintABForTwoThread {

    static CountDownLatch latch = new CountDownLatch(1);

    private volatile static int num = 100;

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
                        try {
                            object.notifyAll();
                            System.out.println(--num + "----------" + Thread.currentThread().getName());
                            object.wait();//释放锁，并阻塞当前线程
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return;
                    }
                }
            }

        }
    }
}
