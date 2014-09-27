package com.test;

public class EvenGenGenerator extends IntGenerator{

	private int currentEvenValue = 0;
	@Override
	public int next() {
		// TODO Auto-generated method stub
		++currentEvenValue;
		++currentEvenValue;
		return currentEvenValue;
	}
	
	public static void main(String args[]){
		EvenCheaker.test(new EvenGenGenerator());
	}

}
