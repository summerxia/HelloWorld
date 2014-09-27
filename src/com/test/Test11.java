package com.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Test11 {
	
	public static long time(Executor executor,int concurrency,final Runnable action) throws InterruptedException{
		final CountDownLatch ready = new CountDownLatch(concurrency);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch done = new CountDownLatch(concurrency);
		
		for(int i=0; i < concurrency ; i++){
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ready.countDown();
					System.out.println("time ");
					try{
						start.await();
						action.run();
					}catch(InterruptedException e){
						Thread.currentThread().interrupt();
					}finally{
						done.countDown();
					}
				}
			});
		}
		ready.await();
		long startNanos = System.nanoTime();
		start.countDown();
		done.await();
		return System.nanoTime() - startNanos;
	}
	
	
	public static void main(String args[]) throws Exception{
		Executor executor = Executors.newFixedThreadPool(10);
		Runnable action = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("I am action");
			}
		};
		
		System.out.println(time(executor,3,action));
		
	}
}
