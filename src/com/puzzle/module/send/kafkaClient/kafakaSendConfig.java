//package com.puzzle.module.send.kafkaClient;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//import org.apache.log4j.Logger;
//
//
//
//public class kafakaSendConfig {
//	public static  Properties props=null;
//	private static Logger log=Logger.getLogger(kafakaSendConfig.class);
//	static{
//		props = new Properties();
//		String path=System.getProperty("user.dir")+File.separator+"config"+File.separator +"kafka"+File.separator+"kafkaProducer.properties";
//		InputStream in=null;
//		try {
//			in=new FileInputStream(path)	;
//			props.load(in);		
//		} catch (IOException e) {
//			log.error(e.getMessage(),e);
//			e.printStackTrace();
//		}finally{
//			try {
//				in.close();
//				path=null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
//	
//}
