/**
 * 
 */
package com.zaid.personal;

import java.util.ArrayList;
//import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//import java.lang.*;
/**
 * @author zaidraza
 *
 */
public class ProdConsLL {
	private int prodThreads ; 
	private int consThreads ;
	public static boolean finishFlag = false;
	private ArrayList<Thread> producerThreads;
	private ArrayList<Thread> consumerThreads;
	public static Semaphore semaphoreConsumer = new Semaphore(0);
	public static Semaphore semaphoreProducer = new Semaphore(1);
	
	public ProdConsLL(int producerThreads , int consumerThreads) {
		this.prodThreads = producerThreads;
		this.consThreads = consumerThreads;
		this.producerThreads = new ArrayList<Thread>();
		this.consumerThreads = new ArrayList<Thread>();
		
	}
	public void execute() throws InterruptedException{
		IntStream.range(0, this.prodThreads)
    		.forEach(i -> this.producerThreads.add(new Thread(new Producer(semaphoreConsumer , semaphoreProducer))));
		IntStream.range(0, this.consThreads)
    		.forEach(i -> this.consumerThreads.add(new Thread(new Consumer(semaphoreConsumer , semaphoreProducer))));
		
		
		this.producerThreads.forEach(Thread::start);
		this.consumerThreads.forEach(Thread::start);
		
		
		TimeUnit.SECONDS.sleep(5);
		
		finishFlag = true;

		try {
			for (Thread consumerThread : this.consumerThreads) {
				consumerThread.join();
			}
			for (Thread producerThread : this.producerThreads) {
				producerThread.join();
			}
			System.out.println("Main program exiting after joining threads");
		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}
	
	
	private static void exitWithMessage() {
		System.out.println("Invalid input, Input must be between [1,10]");
		System.exit(0);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		try {
//			Scanner scan = new Scanner(System.in);
			int producerThreads = Integer.parseInt(args[0]);
//					scan.nextInt();
			int consumerThreads = Integer.parseInt(args[1]);
//					scan.nextInt();
//			scan.close();
			if(producerThreads < 1 || producerThreads > 10 ) {
				 if(consumerThreads < 1 || consumerThreads > 10)
					 exitWithMessage();
			}
			ProdConsLL prodConsLL = new ProdConsLL(producerThreads, consumerThreads);
			prodConsLL.execute();
//			System.out.println("Main program exiting after joining threads");
		}
		catch(InterruptedException interruptedException) {
			exitWithMessage();
		}
		
		
	}
	
	
	
}
