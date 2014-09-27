package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



class ThreadT implements Runnable{

	private int count = 0;
	
	public int getCount(){
		return this.count;
	}
	public void increament(){
		++count;
	}
	
	
	@Override
	public void run() {
		increament();
	}
	
}

public class Test2 {
	public static void main(String[] args) throws InterruptedException{
		ThreadT t1 = new ThreadT();
		ThreadT t2 = new ThreadT();
		
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(t1);
		exec.execute(t2);
		
		TimeUnit.SECONDS.sleep(1);
		
		Print.print(t1.getCount() + "   " + t2.getCount());
		exec.shutdownNow();
	}
}
