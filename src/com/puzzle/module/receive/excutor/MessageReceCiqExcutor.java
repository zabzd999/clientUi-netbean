/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.receive.excutor;

import com.puzzle.module.receive.activemqClient.ActivemqUtils;

/**
 *
 * @author ljs
 */
public class MessageReceCiqExcutor extends Thread {
      
    
    public void run() {
    	while(true){   		
    		 ActivemqUtils.pollMessageFromCiqQueue();
    		 try {
				Thread.sleep(12L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
           
    }
    
}
