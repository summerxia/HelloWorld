package com.test;

public class Test12 {
	
	
	public static void main(String args[]){
		try{
			throw new B("hello");
		}catch(A a){
			System.err.println("DD");
		}catch(Exception e){
			System.err.print("unreachable");
		}
	}
	
	

}
class A extends IllegalArgumentException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9204123311760735297L;
	public A(String msg){
		super(msg);
		System.out.println("I am A");
	}
}

class B extends A{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3751588310271076701L;

	public B(String msg){
		super(msg);
		System.out.println("I am B");
	}
}
