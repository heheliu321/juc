package juc_day01.atguigu.learning;

/**
 * @author n00444323
 * @date 2021/4/17 9:19
 */

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyThreadPool {

    static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<?> result1 = executorService.submit(new ThreadB());

        Future<?> result2 = executorService.submit(new ThreadC(1, 2));

        try {
            System.out.println(result2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
