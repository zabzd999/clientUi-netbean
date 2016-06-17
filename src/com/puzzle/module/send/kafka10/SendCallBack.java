package com.puzzle.module.send.kafka10;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SendCallBack implements Callback {

	private KafkaProducerClient client;
	public SendCallBack(KafkaProducerClient client ){
		this.client=client;
	}
	@Override
	public void onCompletion(RecordMetadata metadata, Exception e) {
		if (metadata != null) {//发送成功	
			System.out.println("发送成功:"+metadata.offset());
			this.client.isblock=false;//说明服务端收到结果了。发送成功了。修改isblock值，让send方法能解除阻塞
			System.out.println("callback修改为false");
		}else{
			System.out.println("inter发送失败");
		}

	}




}
