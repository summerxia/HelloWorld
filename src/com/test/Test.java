package com.test;

import java.util.concurrent.TimeUnit;

public class Test {
	private static volatile boolean stopRequest = false;
	public static void main(String[] args) throws InterruptedException{
		Thread backgroundThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int i = 0;
				while(!stopRequest)
					i++;
				System.out.println(i);
			}
		});
		
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		stopRequest = true;
	}
}
