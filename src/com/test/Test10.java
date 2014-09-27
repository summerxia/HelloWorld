package com.test;

import java.util.Arrays;
import java.util.Set;

public class Test10 {
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		Class<?> cl = null;
		try{
			cl = Class.forName(args[0]);
			
		}catch(ClassNotFoundException e){
			System.err.println("Class Not Found");
		}
		
		Set<String> s = null;
		try{
			
			s = (Set<String>) cl.newInstance();
		}catch(IllegalAccessException e){
			System.err.println("class can't access exception");
		}catch (InstantiationException e) {
			// TODO: handle exception
			System.err.println("class can't instantition");
		}
		
		
		s.addAll(Arrays.asList(args).subList(1, args.length));
		
		System.out.println(s);
		
	}
}
