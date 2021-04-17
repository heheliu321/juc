package juc_day01.atguigu.juc.exam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Create on 2020年1月20日下午1:07:10
 *  @author 脚踏实地，2020涅槃重生     
 *  @version 1.0 
 *https://junzizhibang.blog.csdn.net/article/details/104051510
 * @Description:    
 */
public class ExchangeDataForThread {
    private volatile static String peopleSex = "小仙女";

    private volatile static int count = 3;

    public static void buy() {
        peopleSex = "我是小仙女";
        count++;
        System.out.println("并发编程系列监测结果-----buy()\t" + peopleSex + "count:" + count + Thread.currentThread().getName()
            + "开始....." + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS").format(new Date()));
    }

    public static void sell() {
        count--;
        peopleSex = "我是小帅哥";
        System.out.println("并发编程系列监测结果-----sell()\t" + peopleSex + "count:" + count + Thread.currentThread().getName()
            + "开始....." + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS").format(new Date()));
    }

    public static void main(String[] args) {
        // 1 实例化出来一个 lock
        // 当使用wait 和 notify 的时候 ， 一定要配合着synchronized关键字去使用
        final Object lock = new Object();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (lock) {
                        for (int i = 0; i < 10; i++) {
                            buy();
                            Thread.sleep(100);
                            if (count == 5) {
                                System.out.println("数据开始变化 等待......");
                                lock.wait();
                                lock.notify();
                                countDownLatch.countDown();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            if (count == 5) {
                                sell();
                                lock.notify();
                                lock.wait();
                                countDownLatch.await();
                                System.out.println("数据变化结束......");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, "线程2");

        t1.start();
        t2.start();
    }
}