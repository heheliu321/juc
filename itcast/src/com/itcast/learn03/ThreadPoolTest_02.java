package com.itcast.learn03;

import java.util.concurrent.*;

public class ThreadPoolTest_02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        FutureTask task = new FutureTask(new MyCallableTask());
        executorService.submit(task);
        System.out.println("我是主线程");

        //停止线程池
        executorService.shutdown();
        String result = (String) task.get();
        System.out.println("线程执行完成，返回结果"+result);
    }
}
