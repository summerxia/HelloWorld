package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Timer;
import java.util.TimerTask;

public class Test17 implements Runnable {

	AtomicInteger i = new AtomicInteger(0);

	public int getValue() {
		return i.get();
	}

	private void evenIncreament() {
		i.addAndGet(2);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			evenIncreament();
		}
	}

	public static void main(String args[]) {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.err.println("Abording");
				System.exit(0);
			}
		}, 5000);

		ExecutorService exec = Executors.newCachedThreadPool();

		Test17[] threads = new Test17[3];

		for (int i = 0; i < 3; i++) {
			threads[i] = new Test17();
			exec.execute(threads[i]);
		}
		while (true) {
			for (int i = 0; i < 3; i++) {
				int value = threads[i].getValue();
				// System.out.println("threads"+i +"vaule " + value);
				if (value % 2 != 0) {
					System.out.println(value);
					System.exit(0);
				}
			}
		}
	}
}
