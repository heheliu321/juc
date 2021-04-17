package com.itcast.learn01;

public class ThreadTest {

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        //有问题，只是调用方法
        //thread.run();
        thread.start();
        System.out.println("我是主线程");
    }






}
