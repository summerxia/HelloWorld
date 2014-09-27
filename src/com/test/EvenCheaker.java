package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenCheaker implements Runnable{

	private IntGenerator gp;
	
	
	 public EvenCheaker(int id,IntGenerator gp) {
		// TODO Auto-generated constructor stub
		this.gp = gp;
		Thread.currentThread().setName(id+"");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!gp.isCanceled()){
			int val = gp.next();
			if(val % 2 != 0){
				System.out.println(val +" Thread Name "+Thread.currentThread().getName() + " not even!");
				gp.cancel();
			}
		}
	}
	public static void test(IntGenerator gp ,int count){
		ExecutorService exex = Executors.newCachedThreadPool();
		for(int i = 0; i < count; i++){
			exex.execute(new EvenCheaker(i, gp));
		}
		exex.shutdown();
	}
	public static void test(IntGenerator gp){
		test(gp,10);
	}

}
