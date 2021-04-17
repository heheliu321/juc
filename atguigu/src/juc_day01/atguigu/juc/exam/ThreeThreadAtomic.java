package juc_day01.atguigu.juc.exam;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 每一个java程序员应该都遇到过这个经典的问题，我这里写一个简单的实现方案，基于juc的原子操作类实现三线程打印ABC
 * 原子操作类实现三线程打印
 */

public class ThreeThreadAtomic implements Runnable {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    //线程标志
    private int flag;

    //对象持有的打印字符串
    private String str;

    public ThreeThreadAtomic(String str, int flag) {
        this.flag = flag;
        this.str = str;
    }

    @Override
    public void run() {
        while (true) {
            while (atomicInteger.get() % 3 == flag) {
                System.out.print(str);
                atomicInteger.incrementAndGet();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ThreeThreadAtomic("A", 0)).start();
        new Thread(new ThreeThreadAtomic("B", 1)).start();
        new Thread(new ThreeThreadAtomic("C", 2)).start();
    }
}