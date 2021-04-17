package juc_day01.atguigu.juc.exam;

/**
 * @author n00444323
 * @date 2021/4/16 10:02
 */

public class Test {

    private static volatile int num = 1;

    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1;
        Thread t2;

        /**
         * 并发编程系列监测结果---	 num:1	线程1
         * 发生了死锁
         */

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (num <= 100) {
                    synchronized (object) {
                        object.notify();
                        System.out.println("num:" + num++ + "线程1");
                        try {
                            object.wait();//wait会释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (num <= 100) {
                    synchronized (object) {
                        object.notify();
                        System.out.println("num:" + num++ + "线程2");
                        try {
                            object.wait(); //wait会释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
