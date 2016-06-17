//package com.puzzle.module.send.kafkaClient;
//
//import com.puzzle.module.send.kafkaClient.KafkaSingtonProducerClient;
//
//
//
//public class Main {
//	public static void main(String[] args) throws Exception {
//
//		KafkaSingtonProducerClient client =KafkaSingtonProducerClient.getInstance();		
//		while(true){
//			client.sendMessage("orderTopic0002", "testmessage双方的点点滴滴点点滴滴点点滴滴点点滴滴滴滴");	
//			Thread.sleep(300L);
//		}
//    
////		client.close();
//	}
//}
