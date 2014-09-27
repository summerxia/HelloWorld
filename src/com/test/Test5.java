package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test5 {
	
	
	
	public static<T extends Comparable<T>> T getMax(List<T> list){
		Iterator<T> iterator = list.iterator();
		T result = iterator.next();
		
		while(iterator.hasNext()){
			T t = iterator.next();
			if(t.compareTo(result) > 0)
				result = t;
		}
		
		
		return result;
	}
	
	
	public static void main(String args[]){
		ArrayList<Point> a = new ArrayList<Point>();
		Point p1 = new Point();
		p1.x = 9;
		Point p2 = new Point();
		p2.x = 2;
		a.add(p2);
		a.add(p1);
		
		Point result = getMax(a);
		System.out.println(result.x);
		
		
	}
	
}
class Point<T extends Comparable<T>> implements Comparable<T>{
	public int x;
	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		Point<T> p = (Point<T>) o;
		return this.x - p.x;
	}
}
