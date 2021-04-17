package juc_day01.atguigu.sso.sync;

/**
 * @author n00444323
 * @date 2021/4/17 10:21
 * <p>
 * * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 * *	如：ABCABCABC…… 依次递归
 * <p>
 * ABC ABC ABC
 * 三个线程依次打印ABC ABC ABC
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrintABCForThreeThreadUseSync {

    static CountDownLatch latch = new CountDownLatch(1);

    private volatile static Integer num = 0;

    private static AtomicBoolean isOver = new AtomicBoolean(false);

    private static final Object object = new Object();

    private static Integer MAX = 30;

    public static void main(String[] args) {

        Thread t1 = new Thread(new ThreadB(0, 'A'), "线程1");

        Thread t2 = new Thread(new ThreadB(1, 'B'), "线程2");

        Thread t3 = new Thread(new ThreadB(2, 'C'), "线程3");

        t1.start();
        t2.start();
        t3.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadB implements Runnable {

        private int flag;

        private Character myChar;

        public ThreadB(int flag, Character myChar) {
            this.flag = flag;
            this.myChar = myChar;
        }

        @Override
        public void run() {

            while (true) {
                synchronized (object) {

                    if (num >= MAX) {
                        return;
                    }
                    if (num % 3 != flag) {
                        try {
                            object.wait();
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print(myChar);
                    num++;

                    object.notifyAll();
                }
            }
        }
    }

}
