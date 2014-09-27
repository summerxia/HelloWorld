package com.test;

import java.io.FileNotFoundException;

public class Test9 {

	private static void test1(String baseString){
		long startTime = System.currentTimeMillis();
		String result = "";
		for(int i = 0 ; i < 1000 ; i++){
			result += baseString;
		}
		long endTime = System.currentTimeMillis();
		System.out.println(startTime - endTime);
	}
	
	private static void test2(String baseString) throws FileNotFoundException{
		long startTime = System.currentTimeMillis();
		StringBuilder result = new StringBuilder();
		for(int i = 0 ; i < 1000 ; i++){
			result.append(baseString);
		}
		long endTime = System.currentTimeMillis();
		System.out.println(startTime - endTime);
		throw new IllegalArgumentException("test");
	}
	
	public static void main(String args[]) throws IllegalStateException{
		String baseString = "";
		
		for(int i = 0 ;i < 1000 ; i++){
			baseString += i;
		}
		
		try{
			if(1 > 0)
				throw new IllegalArgumentException("test");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//test1(baseString);
		//test2(baseString);
		
	}
}
