/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.receive.excutor;

import com.puzzle.module.receive.activemqClient.ActivemqUtils;
import com.puzzle.util.FileDto;

import java.io.File;

/**
 * 
 * @author ljs
 */
public class MessageReceCusExcutor extends Thread {

	public void run() {

		while (true) {
			ActivemqUtils.pollMessageFromCusQueue();
			try {
				Thread.sleep(12L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	
	
	public static void main(String[] args) {
		new MessageReceCusExcutor().start();
	}
}
