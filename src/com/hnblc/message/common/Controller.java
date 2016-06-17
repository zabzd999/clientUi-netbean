package com.hnblc.message.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import com.hnblc.message.adaptors.CustomAdaptor;
import com.hnblc.message.utils.Utils;
import com.hnblc.message.vo.Element;

/**
 * 整个程序的控制器
 * @author rechel
 *
 */
public class Controller {
	private static Logger log = LogManager.getLogger(Controller.class.getName());
//	private static org.slf4j.Logger log=LoggerFactory.getLogger(Controller.class);
	private static Controller controller = new Controller();
	private ConcurrentMap<String,ConcurrentMap<String,Element>> elementModules = null;
	private Executors execs = null;
	private Controller() {
		String poolSize = Utils.getProperty("msg.pool.size", "1");
		execs = new Executors(Integer.parseInt(poolSize));
		elementModules = Utils.getConfigs();
//		MapperParser.init();
	}
	
	public static Controller getInstance() {
		return controller;
	}
	
	/**
	 * 获取对应的报文 vo 配置
	 * 
	 * @param name  order / waybill /
	 * @return
	 */
	public Map<String,Element> getAdaptorElement(String name) {
		if(elementModules!=null&&elementModules.containsKey(name)) 
			return elementModules.get(name);
		return null;
	}
	
	public synchronized String[] getRespXmlFromConvertsTasks(String xml,String type) {
		if(type==null||"".equals(type.trim())) return null;
		Map<String,FutureTask<String>> res = new HashMap<String,FutureTask<String>>();
		FutureTask<String> task = null;
		List<String> keys = new ArrayList<String>();
		String[] resps = null;
		String v = null;
		try {
			for(String key:elementModules.keySet()) {
				if(key.equalsIgnoreCase(type)) {
					task = new FutureTask<String>(new CustomAdaptor(elementModules.get(key),xml));
					res.put(key, task);
					this.execs.execute(task);
					keys.add(key);
				}
				key = null;
			}
			resps = new String[keys.size()];
			for(int i=0;i<keys.size();i++) {
				v = res.get(keys.get(i)).get();
				resps[i] = v;
				v = null;
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			res.clear(); res = null;
			task = null;
			keys.clear();
			keys = null;
		}
		return resps;
	}
	
	
}
