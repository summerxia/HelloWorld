package com.test;

import java.util.HashMap;
import java.util.Map;

public class Test7 {
	
	public static void setSize(Size z){
		
	}

	
	public static void main(String args[]){
		for(Size s : Size.values())
			System.out.println(s);
		//setSize(Size.LARGE);
		
		
		System.out.println(Size.getSize("SMALL"));
		System.out.println(Size.SMALL.pay(12));
		
	}
	
}
enum Size{
	SMALL(Temp.Hot),Large(Temp.Not);
	
	private final Temp t;
	Size(Temp temp){
		this.t = temp;
	}
	
	String pay(int index){
		return t.endStory(index);
	}
	
	enum Temp{
		Hot{

			@Override
			String temp(int index) {
				// TODO Auto-generated method stub
				return "hot " + index;
			}
			
		},
		Not{

			@Override
			String temp(int index) {
				// TODO Auto-generated method stub
				return "not " + index;
			}
			
		};
		
		 abstract String temp(int index);
		 
		 String endStory(int index){
			 return temp(index) + " " + "inside strategy";
		 }
		
	}
	
	
	private static final Map<String,Size> stringToEnum = new HashMap<String, Size>();
	static{
		for(Size s : values()){
			stringToEnum.put(s.toString(), s);
		}
	}
	public static Size getSize(String name){
		return stringToEnum.get(name);
	}
}