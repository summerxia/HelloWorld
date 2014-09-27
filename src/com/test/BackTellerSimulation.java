package com.test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


class Customer{
	private final int serviceTime;
	public Customer(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	public int getServiceTime(){
		return this.serviceTime;
	}
	@Override
	public String toString() {
		return "[" + serviceTime + "]";
	}
}

class CustomerLine extends ArrayBlockingQueue<Customer>{
	private static final long serialVersionUID = 3381440946975119999L;

	@Override
	public String toString() {
		if(this.size() == 0)
			return "[empty]";
		StringBuilder sb = new StringBuilder();
		for(Customer customer : this)
			sb.append(customer);
		return sb.toString();
	}
	
	public CustomerLine(int maxLine) {
		super(maxLine);
	}
}

class CustomerGenerator implements Runnable{

	private CustomerLine customerLine;
	private static Random random = new Random(47);
	
	public CustomerGenerator(CustomerLine cl) {
		this.customerLine = cl;
	}
	
	@Override
	public void run() {
		try{
			while(!Thread.interrupted()){
				TimeUnit.MICROSECONDS.sleep(random.nextInt(300));
				Customer customer = new Customer(random.nextInt(1000));
				customerLine.put(customer);
			}
		}catch(InterruptedException e){
			Print.print("CustomerGenerator Interrupted" );
		}
		Print.print("CustomerGenerator terminating");
	}
}

class Teller implements Runnable,Comparable<Teller>{

	private static int counter = 0;
	private final int id = counter++;
	private CustomerLine customerLine;
	private int customersServed = 0;
	private boolean servingCustomerLine = true;
	
	public Teller(CustomerLine line) {
		this.customerLine = line;
	}
	
	@Override
	public synchronized int compareTo(Teller o) {
		return customersServed < o.customersServed ? -1 : (customersServed == o.customersServed ? 0 : 1);
	}
	
	@Override
	public String toString() {
		return "[Teller] " +this.id;
	}

	@Override
	public void run() {
		try{
			while(!Thread.interrupted()){
				Customer customer = customerLine.take();
				TimeUnit.MICROSECONDS.sleep(customer.getServiceTime());
				synchronized (this) {
					this.customersServed ++;
					while(!this.servingCustomerLine)
						wait();
				}
			}
		}catch(InterruptedException e){
			Print.print(this + "Interrupted");
		}
		Print.print(this + "terminating");
	}
	
	public synchronized void doSomethingElse(){
		this.customersServed = 0;
		this.servingCustomerLine = false;
	}
	
	public synchronized void serveCustomerLine(){
		assert !servingCustomerLine:"already serving: " + this;
		this.servingCustomerLine = true;
		notifyAll();
	}
	
}




























public class BackTellerSimulation {
}
