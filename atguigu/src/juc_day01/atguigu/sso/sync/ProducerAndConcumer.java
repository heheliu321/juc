package juc_day01.atguigu.sso.sync;

/**
 * @author n00444323
 * @date 2021/4/17 11:53
 */

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 使用“生产者-消费者模式”编写代码实现：线程A随机间隔（10~200ms）按顺序生成1到100的数字（共100个），
 * 放到某个队列中.3个线程B、C、D即时消费这些数据，线程B打印（向控制台）所有被2整除的数，
 * 线程C打印被3整除的数，线程D打印其它数据，要求数字的打印是有序的（从1到100）
 * 限时40分钟，可以使用IDE及第三方类库
 * 先说一下大牛的评价：我下面写的程序有个问题，如果B线程挂起了，C,D线程运行的话，有可能顺序会不一致。当然不能用三个queue，让B，C,D分别消费，因为太简单了。
 * 应该这样，每次取出一个数来，使用wait(),notify()，先让B运行，然后C运行，然后D运行；就能保证顺序了，下面是我写的程序，暂时没有时间修改，以后有时间了，会进行修改：
 * <p>
 * <p>
 * 线程B输出所有能被2整除的数字
 * * 线程C输出所有能被3整除的数字
 * * 线程D输出所有其他数字
 * <p>
 * <p>
 * 14:48
 **/
public class ProducerAndConcumer {

    private static CountDownLatch latch = new CountDownLatch(3);

    private static Integer num = new Integer(0);

    private static AtomicBoolean isOver = new AtomicBoolean(false);

    private static LinkedList queue = new LinkedList<Integer>();

    public static void main(String[] args) {

        Producer producer = new Producer();
        Thread p1 = new Thread(producer, "线程A1");
        Thread p2 = new Thread(producer, "线程A2");
        Thread p3 = new Thread(producer, "线程A3");
        Thread p4 = new Thread(producer, "线程A4");
        Thread p5 = new Thread(producer, "线程A5");

        Thread c0 = new Thread(new Consumer(0, queue), "线程B");
        Thread c1 = new Thread(new Consumer(1, queue), "线程C");
        Thread c2 = new Thread(new Consumer(2, queue), "线程D");

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

        c0.start();
        c1.start();
        c2.start();

        try {
            latch.await();
            System.out.println(queue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class Producer implements Runnable {

        @Override
        public void run() {

            Random random = new Random();
            while (true) {
                synchronized (queue) {
                    num++;

                    if (num > 100) {
                        return;
                    }

                    System.out.println(Thread.currentThread().getName() + "生产数据=========" + num);
                    queue.add(num);
                    //有数据 激活其他线程
                    queue.notifyAll();
                }

                try {
                    Thread.sleep(10 + random.nextInt(190)); //停顿 10~200毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private int flag;

        private final LinkedList queue;

        public Consumer(int flag, LinkedList queue) {
            this.flag = flag;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (queue) {
                        if (queue.isEmpty() || (getIndex((Integer) queue.peek()) % 3 != flag)) {
                            try {
                                queue.wait(); //阻塞当前线程，并释放锁，设置超时时间
                                if (isOver.get()) {
                                    return;
                                }
                                continue;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        //数据弹出队列
                        System.out.println(Thread.currentThread().getName() + "消费到数据" + queue.poll());

                        if (isOver.get()) {
                            return;
                        }

                        if (num == 100) {
                            isOver.set(true);
                            return;
                        }

                        queue.notifyAll();//唤醒其他阻塞的线程(前提你获得了monitor的owner权限，所有记住wait和notify和notifyAll一定要放到synchronized方法里面)
                    }
                }
            } finally {
                latch.countDown();
            }
        }

    }

    private static int getIndex(Integer num) {
        if (num % 2 == 0) {
            return 0;
        } else if (num % 3 == 0) {
            return 1;
        }
        return 2;
    }
}
