package com.test;

import java.io.IOException;

public class Test14 extends Thread{
	
	private static volatile double d = 1;
	
	public  Test14() {
		setDaemon(true);
		start();
	}
	
	@Override
	public void run(){
		while(true){
			d = d + 0.2;
		}
	}
	
	public static void main(String args[]) throws IOException{
		new Test14();
		System.in.read();
		System.out.println(d);
	}
}
