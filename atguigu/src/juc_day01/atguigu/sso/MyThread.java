package juc_day01.atguigu.sso;

/**
 * @author n00444323
 * @date 2021/4/17 9:19
 */

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyThread {

    static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) {
        new ThreadA().start();

        new Thread(new ThreadB()).start();

        //实现Callable接口
        ThreadC threadC = new ThreadC(1, 2);
        FutureTask<Integer> task = new FutureTask<>(threadC);
        new Thread(task).start();

        try {
            //这个是阻塞的方法，直到有结果返回
            Integer result = task.get();
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }

        try {
            latch.await();
            System.out.println(Thread.currentThread().getName() + "=========over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadA extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            latch.countDown();
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            latch.countDown();
        }
    }

    static class ThreadC implements Callable<Integer> {

        private int a;

        private int b;

        public ThreadC(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer call() throws Exception {
            return a + b;
        }
    }

}
