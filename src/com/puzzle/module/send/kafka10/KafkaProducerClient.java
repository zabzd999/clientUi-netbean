package com.puzzle.module.send.kafka10;

import com.puzzle.util.FileDto;
import com.puzzle.util.SendFailVo;
import java.io.File;
import java.util.Properties;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

/**
 * kafk生产者发送消息方式有两种方式： 同步方式：producer发送完毕后，服务端确认收到后，producer才能认为发送成功
 * 异步方式:producer启动一个监听器listener发送完毕后，。producer发送完毕后不会立即得到是否发送成功的结果，程序不会阻塞。
 * kafk0.10版本后废除了同步通信方式本例为了适应业务需求，将kafka的异步通信 ”改成”
 * 了同步通信。使用者调用send(message)方法后，若未收到服务端的回应，会一直阻塞着， 
 * 注意事项： 1.使用时，次类不再是单实例类。用户自己new
 * KafkaProducerClient()对象。自己new 的KafkaProducerClient对象，自己负责维护
 * (即：用户确定不再发送message了。一定要手动调用close方法）
 * 2.KafkaProducerClient对象为重量级对象，请不要发送一条消息调用一次close()，接着再发送一条消息，再close。建议尽量复用KafkaProducerClient对象，不要频繁创建
 *
 * @author Administrator
 *
 */
public class KafkaProducerClient {

    private static Logger log = Logger.getLogger(KafkaProducerClient.class);
    private Properties props = ProducerConfig.props;
    private Producer<String, String> producer = null; //
    public boolean isblock = Boolean.TRUE;
    public static KafkaProducerClient client = null;
    private Random rnd = new Random();

    public KafkaProducerClient() {
        producer = new KafkaProducer<>(props);

    }

    public static KafkaProducerClient getSingletonInstance() {
        if (client != null) {
            return client;
        }
        synchronized (KafkaProducerClient.class) {
            if (client == null) {
                client = new KafkaProducerClient();
            }
        }

        return client;

    }

    public static void main(String[] args) throws InterruptedException {
        String path = System.getProperty("user.dir") + File.separator
                + "config" + File.separator + "kafka_client_jaas.conf";
        System.setProperty("java.security.auth.login.config", path);
        int i = 1;
        KafkaProducerClient client = new KafkaProducerClient();
        while (true) {
            client.send("orderTopic", "test00001阿双方斯蒂芬斯蒂芬斯蒂芬森的");
//				client.close();//
//				client.send("dddddddddddd");//XXXXXXXXXXXXclient已经关闭，此处再调用send方法将失效，只能重建KafkaProducerClient。
        }
    }

    public SendFailVo send(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                topic, String.valueOf((rnd
                        .nextInt(1000))), message);

        SendFailVo v=new SendFailVo();
        v.failSendFileContent=message;
        SendCallBack callback = new SendCallBack(this);
        this.producer.send(record, callback);
        this.producer.flush();
        long start = 0L;
        while (this.isblock) {
            try {
                if (start >=SendFailVo.WAIT) {
                
                    v.isSuccess=Boolean.FALSE;
                    this.close();
                    break;
                }
                Thread.sleep(10L);
                start = start + 10L;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        this.isblock = Boolean.TRUE;//一旦发送成功isblock就会被改成false，一条message发送成功后，isblock恢复程true

        return v;
    }

    /**
     * 发送消息完毕后,如果以后不再使用producerclient。必须务必调用此方法关闭流,。否则不需要关闭。
     */
    public void close() {
       this. producer.close();
        client =null;

    }
}
