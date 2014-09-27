package com.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Count{
	private int count = 0;
	Random random = new Random(47);
	public synchronized int increament(){
		int temp = count;
		if(random.nextBoolean()){
			Thread.yield();
		}
		return (count = ++temp);
	}
	public synchronized int getCount(){
		return count;
	}
}




public class Test18 {
	
	public static void main(String args[]) throws InterruptedException{
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++){
			service.execute(new Entrance(i));
		}
		TimeUnit.SECONDS.sleep(3);
		Entrance.cancel();
		service.shutdown();
		if(!service.awaitTermination(250, TimeUnit.MILLISECONDS)){
			System.out.println("Some tasks are not terminated");
		}
		System.out.println("Total " + Entrance.sumEntrances());
	}
}
