package com.itcast.learn03;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask task = new FutureTask(new MyCallableTask());
        new Thread(task).start();
        System.out.println("我是主线程");
        //java.util.concurrent.CancellationException
//        task.cancel(true);
        String result = (String) task.get();
        System.out.println("线程执行完成，返回结果"+result);
    }
}
