package com.itcast.lock;

public class SynchronizedTest {

    private  static int num = 0;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 20000; i++) {
            ThreadTask threadTask = new ThreadTask(num);
            Thread task = new Thread(threadTask);
            task.start();
            task.join();
        }

        Thread.sleep(5000);
        System.out.println(num);
    }
}

class ThreadTask implements Runnable {

    private static int temp_num = 0;

    public ThreadTask(int num) {
        this.temp_num = num;
    }

    @Override
    public void run() {
        temp_num++;
    }
}