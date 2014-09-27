package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Horse implements Runnable {
	private static int counter = 0;
	private int id = counter++;
	private int strides = 0;
	private CyclicBarrier barrier;
	private static Random random = new Random(47);

	public synchronized int getStrides() {
		return this.strides;
	}

	public Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					strides += random.nextInt(3);
				}
				barrier.await();
			}
		} catch (InterruptedException e) {

		} catch (BrokenBarrierException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "Horse " + id + " ";
	}

	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.getStrides(); i++) {
			s.append("*");
		}
		s.append(id);
		return s.toString();
	}
}

public class HorseRace {
	private final int FINISH_LINE = 75;
	private List<Horse> horses = new ArrayList<Horse>();
	private CyclicBarrier barrier;

	ExecutorService exec = Executors.newCachedThreadPool();

	public HorseRace(int nHorses, final int pause) {
		barrier = new CyclicBarrier(nHorses, new Runnable() {
			@Override
			public void run() {
				StringBuilder s = new StringBuilder();
				for (int i = 0; i < FINISH_LINE; i++)
					s.append("=");
				Print.print(s.toString());
				for (Horse horse : horses)
					Print.print(horse.tracks());
				for (Horse horse : horses) {
					if (horse.getStrides() >= FINISH_LINE) {
						Print.print(horse + "win");
						exec.shutdownNow();
						return;
					}

				}
			}
		});
		for (int i = 0; i < nHorses; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			exec.execute(horse);
		}

	}
	public static void main(String args[]){
		int nHorses = 7;
		int pause = 200;
		new HorseRace(nHorses, pause);
	}

}
