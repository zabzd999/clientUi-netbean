package com.hnblc.message.adaptors;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


import com.hnblc.message.common.Adaptor;
import com.hnblc.message.plugins.MessageFilter;
import com.hnblc.message.utils.Global;
import com.hnblc.message.utils.MessageUtils;
import com.hnblc.message.utils.Utils;
import com.hnblc.message.vo.Element;
import org.apache.log4j.Logger;

/**
 * 海关端报文
 * @author rechel
 */
public class CustomAdaptor  implements Adaptor{

	private static Logger log = Logger.getLogger(CustomAdaptor.class);
//	private static org.slf4j.Logger log=LoggerFactory.getLogger(CustomAdaptor.class);
	private ConcurrentMap<String,Element> asistMaps = null;
	private String xml = null;
	public CustomAdaptor(ConcurrentMap<String,Element> asistMaps,String xml) {
		this.asistMaps = asistMaps;
		this.xml = xml;
	}
	@Override
	public String call() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream is = null;
        XMLStreamReader reader = null;
        String resp = null;
        try {
           is = new ByteArrayInputStream(xml.getBytes(Global.DEFAULT_CHARATERSET));
           reader = factory.createXMLStreamReader(is);
           resp = MessageUtils.writeMessage(reader, asistMaps);
           
    	   if(this.asistMaps!=null && this.asistMaps.containsKey("filters")) {
    		   List<MessageFilter> filters = this.asistMaps.get("filters").getFilters();
    		   if(filters!=null) {
    			   for(MessageFilter f : filters) {
    				   resp = f.filter(this.xml,resp);
    			   }
    		   }
    	   }
        } catch (Exception e) {
			log.error("Error when Convert message : "+ xml, e);
		} finally {  
           if(reader!=null) {
        	   try {
				reader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
           }
        }
        if(resp==null) return "";
        if("1".equals(Utils.getProperty("msg.header", "1"))) return Global.HEAD.concat(resp);
        else return resp.replace(Global.HEAD, "");
	}

	
}
