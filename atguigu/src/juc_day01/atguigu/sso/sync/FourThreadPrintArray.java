package juc_day01.atguigu.sso.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 给定一个数组[1,2,3,4,5,6,7,8,9....,15]，要求遍历数组，遇到可以同时被3和5整除的数字，打印C；遇到仅能被5整除的数字，打印B；遇到仅能被3整除的数字，打印A；其他打印数字本身；
 * 要求四个线程，每一个线程执行一个打印方法。
 * 我这里使用了lock和condition做了下实现，详细代码如下，希望可以给大家一些参考：
 * 10:39
 * <p>
 * 12A4BA78AB11A1314C16
 */
public class FourThreadPrintArray {

    static CountDownLatch latch = new CountDownLatch(1);

    private volatile static AtomicInteger num = new AtomicInteger(0);

    private static final Object object = new Object();

    static int[] arrays = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    public static void main(String[] args) {

        // %3 %5
        Thread t3 = new Thread(new ThreadB(0, 'C'), "线程3");

        // %5
        Thread t2 = new Thread(new ThreadB(1, 'B'), "线程2");

        // %3
        Thread t1 = new Thread(new ThreadB(2, 'A'), "线程1");

        Thread t4 = new Thread(new ThreadB(3, null), "线程4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

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

                    while (num.get() < arrays.length && getIndex(arrays[num.get()]) % 4 != flag) {
                        try {
                            object.wait(); //阻塞当前线程，并释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (num.get() >= arrays.length) {
                        return;
                    }

                    if (flag == 3) {
                        System.out.print(arrays[num.get()]);
                    } else {
                        System.out.print(myChar);
                    }
                    num.addAndGet(1);

                    object.notifyAll();//唤醒其他阻塞的线程(前提你获得了monitor的owner权限，所有记住wait和notify和notifyAll一定要放到synchronized方法里面)
                }
            }
        }
    }

    private static int getIndex(int num) {
        if (num % 3 == 0 && num % 5 == 0) {
            return 0;
        } else if (num % 5 == 0) {
            return 1;
        } else if (num % 3 == 0) {
            return 2;
        }
        return 3;
    }
}
