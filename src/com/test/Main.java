package com.test;

import java.util.ArrayList;
import java.util.Comparator;

public class Main {
	
	
	class T1<T>{
		
	}
	
	
	private static UnaryFunction<Object> IDENTITY_FUNCTION = new UnaryFunction<Object>() {
		
		@Override
		public Object apply(Object arg) {
			// TODO Auto-generated method stub
			return arg;
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <T>UnaryFunction<T> identityFunction(){
		return (UnaryFunction<T>) IDENTITY_FUNCTION;
	}
	
	public static void main(String[] args){
		UnaryFunction<String> u1 = identityFunction();
		String[] strings = new String[]{"a","b","c"};
		
		for(String s: strings){
			System.out.println(u1.apply(s));
		}
		
		
		Main m = new Main();
		
		
		T1<String> s = null;
		T1<Object> a = m.new T1<Object>();
		//s = (T1<String>)a;
		
		ArrayList<String> abc = new ArrayList<String>();
		abc.add("ccc");
		abc.add("acccvvff");
		
		Comparator<String> test = new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		};
		
		abc.sort(test);
		System.out.println(abc.toString());
		
		
	}
}
