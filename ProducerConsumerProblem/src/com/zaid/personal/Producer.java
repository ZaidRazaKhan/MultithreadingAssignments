/**
 * 
 */
package com.zaid.personal;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author zaidraza
 *
 */
public class Producer implements Runnable{
	private FixedSizedLinkedList list;
	private Semaphore consumerSemaphore;
	private Semaphore producerSemaphore;
	public Producer(Semaphore consumerSemaphore , Semaphore producerSemaphore){
		this.consumerSemaphore = consumerSemaphore;
		this.producerSemaphore = producerSemaphore;
		list = new FixedSizedLinkedList(20);
		System.out.println("Producer started");
	}
	@Override
	public void run() {Random rand = new Random();
    while (!ProdConsLL.finishFlag) {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int value = rand.nextInt(10) + 1;
        try {
            producerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (list.addInt(value)) {
            System.out.println(
                    MessageFormat.format(
                            "Producer added {0} to queue, size = {1}",
                            value,
                            list.size()
                    )
            );
        } else {
            System.out.println("Unable to add more values to queue");
        }
        consumerSemaphore.release();
    }
	}
}
