package juc_day01.atguigu.synchronize;

import java.text.SimpleDateFormat;
import java.util.Date;

/****
 *  实现原子性
 */
public class AchieveAtomicity {
 
	private String username = "usernameTemp";
	private String password = "passwordTemp";
	
	public synchronized void setValue(String username, String password){
		this.username = username;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.password = password;
		System.out.println("并发编程系列监测结果-----setValue"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS").format(new Date()));
		System.out.println("setValue最终结果：username = " + username + " , password = " + password);
	}
	public void getValue(){
		System.out.println("并发编程系列监测结果-----getValue"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS").format(new Date()));
		System.out.println("getValue方法得到：username = " + this.username + " , password = " + this.password);
	}
	
	public static void main(String[] args) throws Exception{
		
		final AchieveAtomicity achieveAtomicity = new AchieveAtomicity();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				achieveAtomicity.setValue("用户名线程测试", "用户密码此处测试");		
			}
		});
		t1.start();
		Thread.sleep(1000);
		achieveAtomicity.getValue();
	}
}