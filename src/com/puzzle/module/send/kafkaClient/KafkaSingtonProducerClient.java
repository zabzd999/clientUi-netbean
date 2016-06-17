//package com.puzzle.module.send.kafkaClient;
//
//import java.util.Random;
//
//import com.puzzle.module.send.kafkaClient.kafakaSendConfig;
//
//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;
//
///**
// * 发生消息的工具类
// *
// * @author ljs
// *
// */
//public class KafkaSingtonProducerClient {
//
//    private static KafkaSingtonProducerClient orderKafkaClient = new KafkaSingtonProducerClient();
//    private static Object locko = new Object();
//
//    Random rnd = new Random();
//
//    Producer<String, String> producer;
//
//    public static KafkaSingtonProducerClient getInstance() {
//     
//        return orderKafkaClient;
//
//    }
//
//    private KafkaSingtonProducerClient() {
//        super();
//
//    }
//
//    private Producer<String, String> getProducer() {
//        if (producer != null) {
//            return producer;
//
//        }
//        synchronized (KafkaSingtonProducerClient.class) {
//            if (producer == null) {
//                producer = new Producer<String, String>(new ProducerConfig(kafakaSendConfig.props));
//            }
//
//        }
//
//        return producer;
//    }
//
//    /**
//     * 发送消息的方法
//     *
//     * @param topic指定消息的topic
//     * @param message一条消息
//     */
//    public void sendMessage(String topic, String message) {
//        KeyedMessage<String, String> data = new KeyedMessage<String, String>(
//                topic, String.valueOf((rnd.nextInt(1000))), message);
//        orderKafkaClient.getProducer().send(data);
//
//    }
//
//    public void close() {
//        producer.close();
//        synchronized (locko) {
//            orderKafkaClient = null;
//        }
//    }
//
//}
