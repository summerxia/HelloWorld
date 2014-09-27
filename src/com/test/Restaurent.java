package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Meal{
	private int orderNum;
	public Meal(int num){
		orderNum = num;
	}
	
	@Override
	public String toString() {
		return "Meal " + orderNum;
	}
}


class WaitPerson implements Runnable{

	private Restaurent restaurent;
	
	public WaitPerson(Restaurent restaurent) {
		this.restaurent = restaurent;
	}
	
	@Override
	public void run() {
		try{
			while(!Thread.interrupted()){
				synchronized(this){
					while(restaurent.meal == null)
						wait();
				}
				System.out.println("WaitPerson got " + restaurent.meal);
				synchronized (restaurent.chef) {
					restaurent.meal = null;
					//Note Here
					restaurent.chef.notifyAll();
				}
			}
		}catch(InterruptedException e){
			System.out.println("WaitPerson Interrupted");
		}
	}
}

class Chef implements Runnable{
	private Restaurent restaurent;
	int count = 0;
	
	public Chef(Restaurent restaurent){
		this.restaurent = restaurent;
	}
	
	@Override
	public void run() {
		try{
			while(!Thread.interrupted()){
				synchronized (this) {
					while(restaurent.meal != null){
						wait();
					}
				}
				if(++count == 10){
					System.out.println("Out Of food");
					restaurent.exec.shutdownNow();
				}
				System.out.println("Order Up");
				synchronized (restaurent.waitPerson) {
					restaurent.meal = new Meal(count);
					restaurent.waitPerson.notifyAll();
				}
			}
		}catch(InterruptedException e){
			System.out.println("Chef Interrupted");
		}
	}
}


public class Restaurent {
	Meal meal = null;
	Chef chef = new Chef(this);
	WaitPerson waitPerson = new WaitPerson(this);
	ExecutorService exec = Executors.newCachedThreadPool();
	
	public Restaurent(){
		exec.execute(chef);
		exec.execute(waitPerson);
	}
	
	
	 public static void main(String[] args){
		 new Restaurent();
	 }
	
}
