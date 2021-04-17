package com.itcast.learn02;

public class ThreadTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new MyTask());
        thread.start();
        System.out.println("我是主线程");
    }
}
