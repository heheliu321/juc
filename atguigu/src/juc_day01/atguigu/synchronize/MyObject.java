package juc_day01.atguigu.synchronize;

import java.text.SimpleDateFormat;
import java.util.Date;
 
public class MyObject {
	public synchronized void method1(){
		try {
			System.out.println("并发编程系列监测结果-----method1()"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS").format(new Date()));
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/** synchronized */
	public synchronized void method2(){
		    System.out.println("并发编程系列监测结果-----method2()"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS").format(new Date()));
			System.out.println(Thread.currentThread().getName());
	}
	public static void main(String[] args) {
		final MyObject mo = new MyObject();
		/**
		 * 分析：
		 * t1线程先持有object对象的Lock锁，t2线程可以以异步的方式调用对象中的非synchronized修饰的方法
		 * t1线程先持有object对象的Lock锁，t2线程如果在这个时候调用对象中的同步（synchronized）方法则需等待，也就是同步
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.method1();
			}
		},"t1");


		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.method2();
			}
		},"t2");


		t1.start();
		t2.start();
	}
}