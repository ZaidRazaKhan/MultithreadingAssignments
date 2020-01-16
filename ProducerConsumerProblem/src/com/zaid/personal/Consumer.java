/**
 * 
 */
package com.zaid.personal;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zaidraza
 *
 */
public class Consumer implements Runnable{
	private FixedSizedLinkedList list;
	private Semaphore consumerSemaphore;
	private Semaphore producerSemaphore;
	public Consumer(Semaphore consumerSemaphore , Semaphore producerSemaphore){
		this.consumerSemaphore = consumerSemaphore;
		this.producerSemaphore = producerSemaphore;
		list = new FixedSizedLinkedList(20);
		System.out.println("Consumer started");
	}
	
	@Override
	public void run() {
		Random rand = new Random();
        while (!ProdConsLL.finishFlag) {
            try {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                consumerSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                int value = list.pop();
                System.out.println(
                        MessageFormat.format(
                                "Consumer removed {0}, computed {0}! = {1}, queue size = {2}",
                                value,
                                calculateFactorial(value),
                                list.size()
                        )
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producerSemaphore.release();
        }
    }

    private int calculateFactorial(int value) {
        return IntStream.rangeClosed(1, value).reduce(1, (int a, int b) -> a * b);
    }
	
}
