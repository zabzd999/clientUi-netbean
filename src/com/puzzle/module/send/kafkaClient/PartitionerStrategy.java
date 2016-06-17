//package com.puzzle.module.send.kafkaClient;
//
//import java.math.BigDecimal;
//import java.util.Random;
//
//import kafka.producer.Partitioner;
//import kafka.utils.VerifiableProperties;
//
//public class PartitionerStrategy implements Partitioner {
//	public PartitionerStrategy(VerifiableProperties props) {
//
//	}
//
//	@Override
//	public int partition(Object obj, int numPartitions) {
//		int partition = 0;
//		if (obj == null) {
//			Random random = new Random();
//			System.out.println("key is null ");
//			return random.nextInt(numPartitions);
//		} else {
//			if (obj instanceof String) {
//				long l = Long.parseLong(obj.toString())%numPartitions;
//				partition = new BigDecimal(l).intValue();				
//				//partition = Math.abs(obj.hashCode())%numPartitions;
//				/**int offset = key.lastIndexOf('.');
//				if (offset > 0) {
//					partition = Integer.parseInt(key.substring(offset + 1))
//							% numPartitions;
//				}**/
//			} else {
//				partition = obj.toString().length() % numPartitions;
//			}
//		}
//		return partition;
//	}
//}
