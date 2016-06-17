package com.puzzle.module.send.kafka10;

import com.puzzle.database.dao.UserDao;
import com.puzzle.util.FileDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ProducerConfig {


	public static  Properties props=null;
	private static Logger log=Logger.getLogger(ProducerConfig.class);
        private static String kafka_producer_perties=UserDao.dao.queryForKafkaProperties().getXmlValue();
	static{
		props = new Properties();
//		String path=System.getProperty("user.dir")+File.separator+"config"+File.separator+"kafka"+File.separator+"kafkaProducer.properties";
		StringReader in=null;
		try {
//			in=new FileInputStream(path)	;
			in=new StringReader(kafka_producer_perties)	;
			props.load(in);
                        in.close();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}finally{
			try {
				in.close();
//				path=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
