package com.hnblc.message.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.hnblc.message.vo.Element;
import com.hnblc.message.vo.ValuePair;

/**
 * 根据报文和标签映射生成报文
 * @author rechel
 *
 */
public class MessageUtils {
	private static Logger log = Logger.getLogger(MessageUtils.class);
//	private static org.slf4j.Logger log=LoggerFactory.getLogger(MessageUtils.class);
	/**
	 * 
	 * @param xml
	 * @param ee
	 */
	public static void test(String xml,ConcurrentMap<String,Element> ee) {  
		XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream is = null;
        XMLStreamReader reader = null;
        log.info("maping:" +ee);
        try {
           is = new ByteArrayInputStream(xml.getBytes(Global.DEFAULT_CHARATERSET));
           reader = factory.createXMLStreamReader(is);
           log.info(writeMessage(reader,ee));
        } catch (Exception e) {
			e.printStackTrace();
		} finally {  
           if(reader!=null) {
        	   try {
				reader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
           }
        }  
    }  
	/**
	 * 根据给定的报文模板和源数据文件，生成特定的报文
	 * @param reader
	 * @param writer
	 * @param emap
	 * @return
	 * @throws Exception
	 */
	public static String writeMessage(XMLStreamReader reader,ConcurrentMap<String,Element> emap) throws Exception{
		String value = null;
		Map<String, ValuePair<String,String>> values = null;
		String xml = emap.get("xml").getChildName();
		String repeatXml = "";
		String innerXml = "";
		String repeatLabel = "";
		String tmp = null;
		while(reader.hasNext()) {
			int type = reader.next();
        	//判断节点类型是否是开始或者结束或者文本节点,之后根据情况及进行处理
        	if(type==XMLStreamConstants.START_ELEMENT) {
        		value = reader.getName().getLocalPart();
        		if(Utils.checkeExistsLabelInModels(value, emap)) {
        			values = Utils.getSingleLabelTargetName(value,emap);
        			value = Utils.getElementContentFromCDATA(reader);
        			if(values != null)
	        			for(String v : values.keySet()) {
	        				tmp = Utils.pickCorrectedValue(values.get(v).getStrict(), value, values.get(v).getDefalt());
	        				xml = xml.replace(Utils.generatedClosedLabel(v, "",false), 
	        						Utils.generatedClosedLabel(v, tmp, Utils.isEnclosedCDATA()));
	        			}
        		}
        		if(Utils.isRepeatLabel(value, emap)) {
        			if("".equals(repeatLabel)) repeatLabel = value;
        			String startLabel = Utils.getLabel(value, true);
        			String endLabel = Utils.getLabel(value, false);
        			if(!value.equalsIgnoreCase(repeatLabel)) {
        				xml = xml.replace(Utils.getLabel(repeatLabel, true).concat(repeatXml).concat(Utils.getLabel(repeatLabel, false))
            					, innerXml);
        				repeatLabel = value;
        				innerXml = "";
        			} else {
        				repeatXml = StringUtils.substringBetween(xml, 
        						startLabel, endLabel);
        				innerXml = innerXml.concat(generaterRepeatClosedXML(repeatXml, emap.get(value), reader));
        			}
//        			log.info("inner : "+innerXml);
        		}
        	}
		}
		return xml.replace(Utils.getLabel(repeatLabel, true).concat(repeatXml).concat(Utils.getLabel(repeatLabel, false))
				, innerXml);
	}
	/**
	 * 生成重复标签的报文
	 * @param modelXml
	 * @param e
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public static String generaterRepeatClosedXML(String modelXml,Element e,XMLStreamReader reader) throws Exception {
		String value = null;
		Map<String, ValuePair<String,String>> values = null;
		String repeatXml = new String(modelXml);
		Map<String,Element> labels = e.getSubElement();
		String tmp = null;
		while(reader.hasNext()) {
			int type = reader.next();
        	//
			if(type==XMLStreamConstants.START_ELEMENT) {
        		value = reader.getName().getLocalPart();
        		if(Utils.checkeExistsLabelInModels(value,labels)) {
        			values = Utils.getSingleLabelTargetName(value,labels);
        			value = Utils.getElementContentFromCDATA(reader);
        			if(values!=null&&!values.isEmpty()) {
        				for(String v:values.keySet()) {
        					tmp = Utils.pickCorrectedValue(values.get(v).getStrict(), value, values.get(v).getDefalt());
        					repeatXml = repeatXml.replace(Utils.generatedClosedLabel(v,"",false), Utils.generatedClosedLabel(v,tmp, Utils.isEnclosedCDATA()));
        				}
        			}
        		}
        	}
        	if(type==XMLStreamConstants.END_ELEMENT) {
        		value = reader.getName().getLocalPart();
        		if(value.equalsIgnoreCase(e.getName())) return repeatXml;
        	}
		}
		return "";
	}
	/**
	 * 获取报文的类型
	 * @param xml
	 * @param label
	 * @return
	 * @throws XMLStreamException 
	 */
	public static String getMessageType(String xml,String label) throws Exception {
		if(label==null||"".equals(label)) return null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = null;
		String key = null;
		try {
			reader = factory.createFilteredReader(
					factory.createXMLStreamReader(new ByteArrayInputStream(xml.getBytes(Global.DEFAULT_CHARATERSET))), 
							new XmlStreamFilterImpl(Arrays.asList("BizInfo",label)));
			while(reader.hasNext()) {
				int type = reader.next();
				if(type==XMLStreamConstants.START_ELEMENT) {
					key = reader.getName().getLocalPart();
					if(label.equalsIgnoreCase(key)) {
						return reader.getElementText();
					}
				}
			}
		} finally {
			if(reader!=null)
					reader.close();
			reader = null;
			factory = null;
			key = null;
		}
		return null;
	}
	
	
//	public static void main(String argsp[]) throws IOException {
//		test(Utils.readFromFile("E:\\workspace\\logical\\MessageAdapter\\config\\order.xml"),
//				Utils.getConfigs().get("order_qp"));
//	}
}
