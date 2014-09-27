package com.test;

import java.util.HashMap;

public class Test6 {
	HashMap<Class<?>,Object> favourits = new HashMap<Class<?>, Object>();
	public void pushEle(Class<?> type,Object instance){
		if(type == null)
			throw new NullPointerException("type is null");
		favourits.put(type, type.cast(instance));
	}
	
	public<T> T getEle(Class<T> type){
		return type.cast(favourits.get(type));
	}
	
	public static void main(String args[]){
		Test6 t6 = new Test6();
		t6.pushEle(Integer.class, new Integer(10));
		t6.pushEle(String.class, "abc");
		//t6.pushEle(null, "abc");
		
		
		//t6.pushEle(Integer.class, "dfff");
		
		System.out.println(t6.getEle(String.class));
	}
}

