package com.test;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Customer {
	private final int serviceTime;

	public Customer(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getServiceTime() {
		return this.serviceTime;
	}

	@Override
	public String toString() {
		return "[" + serviceTime + "]";
	}
}

class CustomerLine extends ArrayBlockingQueue<Customer> {
	private static final long serialVersionUID = 3381440946975119999L;

	@Override
	public String toString() {
		if (this.size() == 0)
			return "[empty]";
		StringBuilder sb = new StringBuilder();
		for (Customer customer : this)
			sb.append(customer);
		return sb.toString();
	}

	public CustomerLine(int maxLine) {
		super(maxLine);
	}
}

class CustomerGenerator implements Runnable {

	private CustomerLine customerLine;
	private static Random random = new Random(47);

	public CustomerGenerator(CustomerLine cl) {
		this.customerLine = cl;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
				Customer customer = new Customer(random.nextInt(1000));
				customerLine.put(customer);
			}
		} catch (InterruptedException e) {
			Print.print("CustomerGenerator Interrupted");
		}
		Print.print("CustomerGenerator terminating");
	}
}

class Teller implements Runnable, Comparable<Teller> {

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
		return customersServed < o.customersServed ? -1
				: (customersServed == o.customersServed ? 0 : 1);
	}

	@Override
	public String toString() {
		return "[Teller] " + this.id;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Customer customer = customerLine.take();
				TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
				synchronized (this) {
					this.customersServed++;
					while (!this.servingCustomerLine)
						wait();
				}
			}
		} catch (InterruptedException e) {
			Print.print(this + "Interrupted");
		}
		Print.print(this + "terminating");
	}

	public synchronized void doSomethingElse() {
		this.customersServed = 0;
		this.servingCustomerLine = false;
	}

	public synchronized void serveCustomerLine() {
		assert !servingCustomerLine : "already serving: " + this;
		this.servingCustomerLine = true;
		notifyAll();
	}
}

class TellerManager implements Runnable {

	private ExecutorService exec;
	private CustomerLine customers;
	private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();
	private Queue<Teller> tellersDoingOtherThings = new LinkedList<Teller>();
	private int adjustmentPeriod;

	public TellerManager(ExecutorService exec, CustomerLine customers,
			int adjustmentPeriod) {
		this.exec = exec;
		this.customers = customers;
		this.adjustmentPeriod = adjustmentPeriod;

		Teller teller = new Teller(customers);
		exec.execute(teller);
		workingTellers.add(teller);
	}

	public void adjustTellerNumber() {
		if (customers.size() / workingTellers.size() > 2) {
			if (tellersDoingOtherThings.size() > 0) {
				Teller teller = tellersDoingOtherThings.remove();
				teller.serveCustomerLine();
				workingTellers.offer(teller);
				return;
			}
			Teller teller = new Teller(customers);
			exec.execute(teller);
			workingTellers.add(teller);
			return;
		}
		if (workingTellers.size() > 1
				&& customers.size() / workingTellers.size() < 2) {
			reassignOneTeller();
		}
		if (customers.size() == 0) {
			while (workingTellers.size() > 1) {
				reassignOneTeller();
			}
		}
		
	}

	private void reassignOneTeller() {
		Teller teller = workingTellers.poll();
		teller.doSomethingElse();
		tellersDoingOtherThings.offer(teller);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TellerManager ";
	}
	

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()){
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
				adjustTellerNumber();
				System.out.println(customers + "{");
				for (Teller teller : workingTellers)
					System.out.println(teller.toString() + " ");
				System.out.println("}");
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " terminated");
	}

}

public class BackTellerSimulation {
	private static final int MAX_LINE_SIZE = 50;
	private static final int ADJUSTMENT_PERIOD = 1000;

	public static void main(String args[]) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
		exec.execute(new CustomerGenerator(customers));
		exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
		TimeUnit.SECONDS.sleep(45);
		exec.shutdownNow();
	}

}
