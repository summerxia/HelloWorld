package com.test;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Entrance implements Runnable{
	private int number;
	private static List<Entrance> entrances = new ArrayList<Entrance>();
	private static Count count= new Count();

	private final int id;
	private static volatile boolean canceled = false;
	
	public static void cancel(){
		Entrance.canceled = true;
	}
	
	public Entrance(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		entrances.add(this);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!canceled){
			synchronized (this) {
				number++;
			}
			System.out.println(this + "Total :" +count.increament());
			try{
				TimeUnit.MILLISECONDS.sleep(100);
				
			}catch(InterruptedException e){
				System.out.println("sleep Interrupted");
			}
		}
		System.out.println("Stoping " + this);
		
	}
	
	public synchronized int getValue(){
		return number;
	}
	
	@Override
	public String toString(){
		return "Entrance "+ id + ": " +getValue();
	}
	
	public static int sumEntrances(){
		int sum = 0;
		for(Entrance entrance : entrances){
			sum += entrance.getValue();
		}
		return sum;
	}
	
	public static int getTotalCount	(){
		return count.getCount();
	}
	
}
