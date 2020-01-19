package com.zaid.personal.driver;

import com.zaid.personal.actor.Chopstick;
import com.zaid.personal.actor.Philosopher;

public class DiningPhilosopher {
	public static void main(String[] args) {
		Chopstick[] chop = new Chopstick[5];
		for (int i = 0; i < 5; i++) {
			chop[i] = new Chopstick(i);
		}
		Philosopher[] philo = new Philosopher[5];
		for (int i = 0; i < 5; i++) {
			philo[i] = new Philosopher(i, chop[i], chop[(i + 1) % 5]);
		}
	}

}
