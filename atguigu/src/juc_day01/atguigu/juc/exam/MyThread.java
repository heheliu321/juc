package juc_day01.atguigu.juc.exam;

/**
 * @author n00444323
 * @date 2021/4/16 10:02
 */

public class MyThread extends Thread {

    public static volatile Integer num = 1;

    private static Object object = new Object();

    /**
     * 并发编程系列监测结果---	 num:1	线程1
     * 发生了死锁
     */

    @Override
    public void run() {
        while (num <= 100) {
            synchronized (object) {
                object.notify();
                System.out.println("num:" + num++ + "------" + Thread.currentThread().getName());
                try {
                    object.wait();//wait会释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        new Thread(t1, "线程1").start();
        new Thread(t2, "线程2").start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
