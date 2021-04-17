package juc_day01.atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 一、用于解决多线程安全问题的方式：
 *
 * synchronized:隐式锁
 * 1. 同步代码块
 *
 * 2. 同步方法
 *
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class TestLock {

    public static Object object = new Object();

    public static void main(String[] args) {
        Ticket ticket = new Ticket(object);

        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }

}

class Ticket implements Runnable {

    private int tick = 100;

    private Object object;

    private Lock lock = new ReentrantLock();

    public Ticket(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        while (true) {

            // lock.lock(); //上锁
            synchronized (object) {
                if (tick > 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                }else {
                	return;
				}
            }

            // }finally{
            // 	lock.unlock(); //释放锁
            // }
        }
    }

}