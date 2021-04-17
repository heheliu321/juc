package juc_day01.atguigu.juc.exam.producerandconsumer;

import java.util.LinkedList;
import java.util.Random;

/**
 * 线程A每隔（10-200ms）按序产生一个数（1-100）。把它们放到某个地方。
 * 线程B输出所有能被3整除的数字
 * 线程C输出所有能被5整除的数字
 * 线程D输出所有其他数字
 * 输出的数字必须按序输出（1-100）
 * 时间40ms
 */

public class MultThreadsTest {
    static final int MAX_VALUE = 20;
    static LinkedList<Integer> list = new LinkedList<>();

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void producer() throws InterruptedException {
            Random rd = new Random();
            for (int i = 1; i <= 100; ++i) {
                int rand = rd.nextInt(191) + 10;
                Thread.sleep(rand);
                synchronized (list) {
                    if(list.size() > MAX_VALUE){
                        list.wait();
                    }
                    list.add(i);
                    list.notifyAll();
                }
            }
        }
    }

    static class ThreadB extends Thread{
        @Override
        public void run() {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void consumer() throws InterruptedException {
           synchronized (list){
               while (true){
                   if (list.size() == 0 || list.peek() % 3 != 0){
                       //没有元素或者不应该他输出,释放锁，并等待
                       list.wait();
                       continue;
                   }
                   System.out.println("ThreadB-->"+list.poll());
                   //输出之后，唤醒其他线程
                   list.notifyAll();
               }
           }
        }
    }

    static class ThreadC extends Thread{
        @Override
        public void run() {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void consumer() throws InterruptedException {
            synchronized (list){
                while (true){
                    if (list.size() == 0 || list.peek() % 5 != 0){
                        //没有元素或者不应该他输出,释放锁，并等待
                        list.wait();
                        continue;
                    }
                    System.out.println("ThreadC-->"+list.poll());
                    //输出之后，唤醒其他线程
                    list.notifyAll();
                }
            }
        }
    }
    static class ThreadD extends Thread{
        @Override
        public void run() {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void consumer() throws InterruptedException {
            synchronized (list){
                while (true){
                    if (list.size() == 0 || list.peek() % 3 == 0 || list.peek() % 5 == 0){
                        //没有元素或者不应该他输出,释放锁，并等待
                        list.wait();
                        continue;
                    }
                    System.out.println("ThreadD-->"+list.poll());
                    //输出之后，唤醒其他线程
                    list.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        ThreadC c = new ThreadC();
        ThreadD d = new ThreadD();
        a.start();
        b.start();
        c.start();
        d.start();

    }
}

