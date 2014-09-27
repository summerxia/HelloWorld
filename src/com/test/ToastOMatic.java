package com.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.test.Print;

class Toast {
	public enum Status {
		DRY, BUTTERED, JAMMED,JELLYED
	}

	private Status status = Status.DRY;
	private final int id;

	public Toast(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return this.status;
	}

	public void jelly(){
		this.status = Status.JELLYED;
	}
	
	public void butter() {
		this.status = Status.BUTTERED;
	}

	public void jam() {
		this.status = Status.JAMMED;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Toast " + id + ": " + this.status;
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast> {
	private static final long serialVersionUID = -3894397931090272334L;
}

class Toaster implements Runnable {

	private ToastQueue toastQueue;
	private int count = 0;
	private Random random = new Random(47);

	public Toaster(ToastQueue tq) {
		this.toastQueue = tq;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MICROSECONDS.sleep(100 + random.nextInt(500));
				Toast toast = new Toast(count++);
				Print.print(toast);
				toastQueue.put(toast);
			}
		} catch (InterruptedException e) {
			Print.print("Toast Interrupted");
		}
		Print.print("Toaster Off");
	}
}

class Butterer implements Runnable {
	private ToastQueue dryQueue, butteredQueue;
	public Butterer(ToastQueue dryQueue, ToastQueue butteredQueue) {
		this.dryQueue = dryQueue;
		this.butteredQueue = butteredQueue;
	}
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				//TimeUnit.MILLISECONDS.sleep(150);
				Toast toast = dryQueue.take();
				toast.butter();
				Print.print(toast);
				butteredQueue.put(toast);
			}
		} catch (InterruptedException e) {
			Print.print("Buttered Queue Interrupted");
		}
		Print.print("Butter Off");
	}
}

class Jelly implements Runnable{
	private ToastQueue dryQueue,jellyedQueue;
	public Jelly(ToastQueue dry,ToastQueue jelly){
		this.dryQueue = dry;
		this.jellyedQueue = jelly;
	}
	@Override
	public void run() {
		try{
			while(! Thread.interrupted()){
				//TimeUnit.MILLISECONDS.sleep(200);
				Toast toast = dryQueue.take();
				toast.jelly();
				Print.print(toast);
				jellyedQueue.put(toast);
				
			}
		}catch(InterruptedException e){
			
		}
		Print.print("Jelly Off");
	}
	
}


class Jammer implements Runnable {
	private ToastQueue butteredQueue, jellyedQueue,finishedQueue;
	Random random = new Random(47);

	public Jammer(ToastQueue butteredQueue,ToastQueue jellyedQueue,ToastQueue finishedQueue) {
		this.butteredQueue = butteredQueue;
		this.finishedQueue = finishedQueue;
		this.jellyedQueue = jellyedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast;
				if(random.nextBoolean()){
					toast = butteredQueue.take();
				}else{
					toast = jellyedQueue.take();
				}
				toast.jam();
				Print.print(toast);
				finishedQueue.put(toast);
			}
		} catch (InterruptedException e) {
			Print.print("Jammer interrupted");
		}
		Print.print("Jammer Off");
	}

}

class Eater implements Runnable {
	private ToastQueue finishedQueue;
	private int counter = 0;

	public Eater(ToastQueue finishedQueue) {
		this.finishedQueue = finishedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = finishedQueue.take();
				//Print.print(toast);
//				if (toast.getId() != counter++
//						|| toast.getStatus() != Toast.Status.JAMMED) {
//					Print.print(">>>>Error " + toast);
//					Print.print("toast id: " + toast.getId() + " counter: " + counter );
//					System.exit(1);
//				}
			}
		} catch (InterruptedException e) {
			Print.print("Eater interrupted");
		}
		Print.print("Eat Off");
	}

}

public class ToastOMatic {

	public static void main(String args[]) throws InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
		ToastQueue dryQueue = new ToastQueue(),
				   butteredQueue = new ToastQueue(),
				   finishedQueue = new ToastQueue(),
				   jellyedQueue = new ToastQueue();
		
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butterer(dryQueue, butteredQueue));
		exec.execute(new Jelly(dryQueue, jellyedQueue));
		exec.execute(new Jammer(butteredQueue, jellyedQueue,finishedQueue));
		exec.execute(new Eater(finishedQueue));
		
		TimeUnit.SECONDS.sleep(1);
		exec.shutdownNow();
		
	}
				
}
