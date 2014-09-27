package com.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Test16 {
	private ReentrantLock lock = new ReentrantLock();
	public void untimed(){
		boolean captured = lock.tryLock();
		try{
			System.out.println("tryLock(): " + captured);
		}finally{
			if(captured){
				lock.unlock();
			}
		}
	}
	public void timed(){
		
		
		
		
		boolean captured = false;
		try {
			captured = lock.tryLock(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			System.out.println("lock.tryLock(2,TimeUint.SECONDS): " + captured);
		}finally{
			if(captured){
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args){
		final Test16 test = new Test16();
		test.untimed();
		test.timed();
		new Thread(){
			{
				setDaemon(true);
			}
			@Override
			public void run() {
				test.lock.lock();
				System.out.println("acquired");
			};
		}.start();
		Thread.yield();
		test.untimed();
		test.timed();
	}
}
