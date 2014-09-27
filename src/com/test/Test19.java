package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Thead1 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			synchronized(this){
				System.out.println("waiting");
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interruppted");
		}
		System.out.println("Thread 1 ");
	}
	
}

class THread2 implements Runnable{

	Thead1 thread1;
	
	public THread2(Thead1 thread1){
		this.thread1 = thread1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(thread1){
			
			thread1.notify();
		}
	}
	
}

public class Test19 {

	public static void main(String args[]) throws InterruptedException{
		ExecutorService exe = Executors.newCachedThreadPool();
		Thead1 thread1 = new Thead1();
		exe.execute(thread1);
		TimeUnit.SECONDS.sleep(3);
		exe.execute(new THread2(thread1));
		TimeUnit.SECONDS.sleep(1);
		exe.shutdownNow();
	}
	
	
}
