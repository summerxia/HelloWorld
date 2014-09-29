package com.test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class Test8 {
	
	
	public static void main(String args[]){
		int a1 = 15 & 31;
		
		
		
		
		System.out.println(a1); 
		
		
//		System.out.println(Phase.Transition.from(Phase.SOLID, Phase.LIQUID));
//		
//		int[] digits = {1,2,3,4,5,6};
//		
//		System.out.println(Arrays.toString(digits));
//		
//		
//		Phase.apply(EnumSet.of(Phase.SOLID,Phase.GAS));
		
		
	}
	
	
}
enum Phase{
	SOLID,LIQUID,GAS;
	
	
	public static void apply(Set<Phase> phases){
		for(Phase p : phases){
			System.out.println(p);
		}
	}
	
	
	enum Transition{
		MELT(SOLID,LIQUID),FREEZE(LIQUID,SOLID),
		BOLT(LIQUID,GAS),CONDENSE(GAS,LIQUID),
		SUBLIME(SOLID,GAS),DEPOSIT(GAS,SOLID);
		
		private final Phase src;
		private final Phase dst;
		
		Transition(Phase src,Phase dst){
			this.src = src;
			this.dst = dst;
		}
		
		private static final Map<Phase,Map<Phase,Transition>> m =
				new EnumMap<Phase,Map<Phase,Transition>>(Phase.class);
		
		static{
			for(Phase p : Phase.values())
				m.put(p, new EnumMap<Phase,Transition>(Phase.class));
			for(Transition trans : Transition.values())
				m.get(trans.src).put(trans.dst, trans);
		}
		
		public static Transition from(Phase src,Phase dst){
			return m.get(src).get(dst);
		}
		
	}
}