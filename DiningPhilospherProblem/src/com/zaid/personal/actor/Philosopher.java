package com.zaid.personal.actor;

import java.util.Random;

public class Philosopher extends Thread {
	private int i;
	private Random rand = new Random();
	private Chopstick leftChopstick, rightChopstick;
	private static int ponder = 10;
	private static volatile boolean[] flag = { true, true, true, true, true }; // ensure volatile types consistency in
																				// memory//

	public Philosopher(int i, Chopstick left, Chopstick right) {
		this.i = i;
		this.leftChopstick = left;
		this.rightChopstick = right;
		start();
	}

	public String toString() {
		return "Philosopher " + i;
	}

	public static synchronized boolean test(int i) // testing and setting//
	{
		if (flag[i] && flag[(i + 1) % 5]) {
			flag[i] = flag[(i + 1) % 5] = false;
			return true;
		}
		return false;
	}

	public static synchronized void testAndWait(int i) {
		while (!test(i)) {
			try {
				Philosopher.class.wait(); // waiting//
			} catch (InterruptedException e) {
			}
		}
	}

	public static void release(int i) {
		flag[i] = flag[(i + 1) % 5] = true;
		synchronized (Philosopher.class) {
			Philosopher.class.notifyAll(); // announcing//
		}
	}

	public void think() {
		System.out.println(this + " starts thinking");
		try {
			Thread.sleep(rand.nextInt(ponder));
		} catch (InterruptedException e) {
		}
	}

	public void eat() {
		int n = rand.nextInt(100);
		if (n % 2 == 0) {
			synchronized (leftChopstick) {
				System.out.println(this + " grabs " + leftChopstick);
			}
			synchronized (rightChopstick) {
			}
			// System.out.println(this+" grabs " +rightChopstick+", Dining"); }
		} else {
			synchronized (rightChopstick) {
				System.out.println(
						this + " grabs " + rightChopstick + " (left),ready to take" + leftChopstick + " (right)");
				synchronized (leftChopstick) {
					System.out.println(this + " starts" + " eating");
				}
			}
		}
	}

	public void run() {
		while (true) {
			think();
			testAndWait(i);
			eat();
			release(i);
		}
	}
}
