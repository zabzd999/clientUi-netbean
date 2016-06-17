package com.hnblc.message.common;

import java.util.concurrent.ExecutorService;

/**
 * 线程池
 * @author rechel
 *
 */
public class Executors {

	ExecutorService executors = null;
	
	public Executors(Integer threads) {
		executors = java.util.concurrent.Executors.newFixedThreadPool(threads);
	}
	
	public void execute(Runnable runable) throws Exception {
		if(runable!=null&&executors!=null) {
			executors.execute(runable);
		} else {
			throw new Exception("No Task or Executors to run...");
		}
	}
}
