package com.atguigu.juc;

/*
    可以使用synchronized实现
 */
public class TestVolatile01 {

    public static void main(String[] args) {
        ThreadDemo01 td = new ThreadDemo01();
        new Thread(td).start();

        while (true) {
            synchronized (td) {
                if (td.isFlag()) {
                    System.out.println("------------------");
                    break;
                }
            }
        }

    }

}

class ThreadDemo01 implements Runnable {

    private boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}