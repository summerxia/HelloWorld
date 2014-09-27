package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Car {
	private boolean waxOn = false;

	public synchronized void waxed() {
		waxOn = true;
		notifyAll();
	}

	public synchronized void buffed() {
		waxOn = false;
		notifyAll();
	}

	public synchronized void waitingForWaxing() throws InterruptedException {
		while (waxOn == false) {
			wait();
		}
	}
	public synchronized void waitingForBuffing() throws InterruptedException {
		while(waxOn == true){
			wait();
		}
	}
}

class WaxOn implements Runnable{
	
	private Car car;
	public WaxOn(Car c){
		car = c;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				System.out.println("WaxOn!");
				TimeUnit.MICROSECONDS.sleep(200);
				car.waxed();
				car.waitingForBuffing();
			}
		}catch(InterruptedException e){
			System.out.println("Exiting via Interrupted");
		}
		System.out.println("Ending WaxOn Task");
	}
}

class WaxOff implements Runnable{
	private  Car car;
	
	public WaxOff(Car c){
		car = c;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(!Thread.interrupted()){
				car.waitingForWaxing();
				System.out.println("Waxed Off");
				TimeUnit.MICROSECONDS.sleep(200);
				car.buffed();
			}
		}catch(InterruptedException e){
			System.out.println("Exiting via Interrupted");
		}
		System.out.println("Ending WaxOff Task");
	}
	
}

public class WaxOMatic {
	public static void main(String args[]) throws InterruptedException{
		Car car = new Car();
		ExecutorService exe = Executors.newCachedThreadPool();
		exe.execute(new WaxOff(car));
		exe.execute(new WaxOn(car));
		TimeUnit.SECONDS.sleep(1);
		exe.shutdownNow();
	}
	
	 
}
