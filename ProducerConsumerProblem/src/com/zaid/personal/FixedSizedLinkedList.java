/**
 * 
 */
package com.zaid.personal;

import java.util.LinkedList;

/**
 * @author zaidraza
 *
 */
public class FixedSizedLinkedList {
	public static LinkedList<Integer> list = new LinkedList<Integer>();
	private int limit;

    public FixedSizedLinkedList(int limit) {
		// TODO Auto-generated method stub
    	this.limit = limit;
	}

    public boolean addInt(Integer e) {
        if (list.size() >= limit) {
            return false;
        }
        return list.add(e);
    }
    @SuppressWarnings("finally")
	public int pop() throws InterruptedException {
    	try {
    		if(list.isEmpty()) {
        		wait();
        	}
        	
    	}
    	catch(InterruptedException interruptedException) {
    		interruptedException.printStackTrace();
    	}
    	finally{
    		return (list.pop());
    	}
    }
    public int size() {
    	return list.size();
    }
//	public boolean addInt
	
}
