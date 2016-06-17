package com.hnblc.message.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import com.hnblc.message.plugins.MessageFilter;
import com.hnblc.message.vo.Element;
import com.hnblc.message.vo.ValuePair;
import com.puzzle.database.dao.UserDao;
import com.puzzle.util.FileDto;
import java.io.StringReader;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;

public class Utils {

    private static Logger log = Logger.getLogger(Utils.class);
    private static Properties p = null;
    private static ConcurrentMap<String, String> MTC = null;
    private String configProperties =null;
    private String order_cus_adaptor_config = null;
    private static Utils utilse = new Utils();

    private Utils() {
        p = new Properties();
        String key = null;
        String v = null;
        try {
        configProperties = UserDao.dao.queryForConfigPerperties(FileDto.getSingleInstance().type).getXmlValue();
        order_cus_adaptor_config = UserDao.dao.queryForOrder_cus_adaptorXml(FileDto.getSingleInstance().type).getXmlValue();
 
            
            StringReader in = new StringReader(configProperties);
//			p.load(in);
            p.load(in);
            in.close();
            Set<Object> keys = p.keySet();
            if (keys != null) {
                MTC = new ConcurrentHashMap<String, String>();
                for (Object k : keys) {
                    key = k.toString().toUpperCase();
                    if (key.contains("MSG.TYPE")) {
                        v = p.getProperty(k.toString());
                        if (v != null && !"".equals(v.trim())) {
                            MTC.put(key.substring(key.lastIndexOf(".") + 1), v.trim().toUpperCase());
                        }
                    } else if ("MSG.CDATA".equals(key)) {
                        v = p.getProperty(k.toString(), "0");
                        if (v == null || "".equals(v.trim())) {
                            v = "0";
                        }
                        Global.CDATA_ADD = Integer.parseInt(v);
                    }
                }
                log.debug("Message Type configs : " + MTC);
            }
            if (MTC == null || MTC.isEmpty()) {
                log.error("Message Type Configs NULL in [ config/config.properties ] ,Please checked it !",
                        new Exception("Class:com.hnblc.message.Utils -- 56 line. "));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
    }

    public static Utils getSingleInstance() {
        if (utilse != null) {
            return utilse;
        }
        synchronized (Utils.class) {
            utilse = new Utils();

        }
        return utilse;
    }

    static {

    }

    /**
     * 获取报文转换对应的配置报文
     *
     * @return
     */
    public static ConcurrentMap<String, ConcurrentMap<String, Element>> getConfigs() {
//		String path = System.getProperty("user.dir");
//		path = path.concat(File.separator).concat("config");
//		File[] files = new File(path).listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File pathname) {
//				if(pathname.isFile()&&pathname.getName().matches("^.*_[aA][dD][aA][pP][tT][oO][rR]\\.xml$")) return true;
//				return false;
//			}
//		});
        ConcurrentMap<String, ConcurrentMap<String, Element>> es = new ConcurrentHashMap<String, ConcurrentMap<String, Element>>();
//		if(files!=null&&files.length>0) {
        String name = null;
        String file = null;
//			for(int i=0;i<files.length;i++) {
//				file = path.concat(File.separator).concat(files[i].getName());
//				log.info("init convert xml config file : [ "+ file+" ]");
//				name = file.substring(0,file.lastIndexOf("_"));
//				name = name.substring(name.lastIndexOf("\\")+1);
//				name = name.substring(name.lastIndexOf("/")+1);
        es.put("ORDER_CUS", initConfigFile(Utils.getSingleInstance().order_cus_adaptor_config));
//                                System.out.println("getConfigs class name:"+name);
        file = null;
        name = null;
//			}
//		}
        return es;
    }

    /**
     * 根据文件名加载为标签映射表
     *
     * @param path
     * @return
     */
    private static ConcurrentMap<String, Element> initConfigFile(String path) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        try {
//			reader = factory.createXMLStreamReader(new FileInputStream(path));
            reader = factory.createXMLStreamReader(new StringReader(path));
            ConcurrentMap<String, Element> es = new ConcurrentHashMap<String, Element>();
            String xml = parseXmlContents(reader, es, null, null);
            Element e = new Element();
            e.setName("xml");
            e.setChildName(xml);
            es.put("xml", e);
            reader.close();
            return es;
        } catch (FileNotFoundException x) {
            x.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析报文
     *
     * @param reader
     * @param elements
     * @param xml
     * @param endElement
     * @param key label映射表对应的key 如： CIQ
     * @return
     * @throws Exception
     */
    private static String parseXmlContents(XMLStreamReader reader, Map<String, Element> elements, String xml, String endElement) throws Exception {
        String key = null;
        String v = null;
        String message = xml == null ? "" : xml;
        String label = null;
        Element e = null;
        Map<String, String> labelMaps = new HashMap<String, String>();
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                label = reader.getName().getLocalPart();
                if ("Filters".equalsIgnoreCase(label)) {
                    Element filter = new Element();
                    filter.setName("filters");
                    filter.setFilters(parseFilterFromConfig(reader, xml, "Filters"));
                    elements.put("filters", filter);
                } else {
                    //key = reader.getAttributeValue(0);
                    key = reader.getAttributeValue(null, "leaf");
                    //leaf=0的标签及子标签解析
                    if ("0".equalsIgnoreCase(key)) {
                        v = reader.getName().getLocalPart();

                        /**
                         * 检测blank 标签
                         */
                        //label = reader.getAttributeValue(2);
                        label = reader.getAttributeValue(null, "name");
                        labelMaps.put(v, label);
                        message = message.concat(getLabel(label, true));
                        endElement = endElement == null ? v : endElement;
	//					log.info("0message:" +message);
                        //获取repeat属性,分类解析
                        //key = reader.getAttributeValue(1);
                        key = reader.getAttributeValue(null, "repeat");
                        if ("1".equals(key)) {
                            message = parseSingleLabels(reader, elements, message, v)
                                    .concat(getLabel(label, false));
                        } else if ("2".equals(key)) {
                            message = parseDoubleLabels(reader, elements, message, v)
                                    .concat(getLabel(label, false));
                        } else if ("3".equals(key)) {
                            e = new Element();
                            e.setName(v);
                            e.setSubElement(new HashMap<String, Element>());
                            elements.put(e.getName(), e);
                            message = parseTreesLabels(reader, elements, message, v)
                                    .concat(getLabel(label, false));
                        }
                    }
                }
            }
            if (type == XMLStreamConstants.END_ELEMENT) {
                v = reader.getName().getLocalPart();
                if (labelMaps.containsKey(v)) {
                    message = message.concat(getLabel(labelMaps.get(v), false));
                }
                if (v.equalsIgnoreCase(endElement)) {
                    return message;
                }
            }
        }
        return message;
    }

    private static List<MessageFilter> parseFilterFromConfig(XMLStreamReader reader, String xml, String endElement) throws Exception {
        String name = null;
        String content = null;
        List<MessageFilter> filters = new ArrayList<MessageFilter>();
        MessageFilter filter = null;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                name = reader.getName().getLocalPart();
                if ("filter-class".equalsIgnoreCase(name)) {
                    content = reader.getAttributeValue("", "path");
                    if (content != null && !"".equals(content.trim())) {
                        try {
                            filter = (MessageFilter) Class.forName(content).newInstance();
                            log.debug("Create Filter [ " + filter + " ]");
                            filters.add(filter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if ("property".equalsIgnoreCase(name)) {
                    name = reader.getAttributeValue(0);
                    content = reader.getElementText();
                    if (filter != null) {
                        filter.setProperty(name, content);
                        log.debug("Set property [name: " + name + " , value: " + content + " ] already !");
                    } else {
                        log.error("Set property [name: " + name + " , value: " + content + " ] Error ! Init Filter Class First !");
                    }
                }
            }
            if (type == XMLStreamConstants.END_ELEMENT) {
                name = reader.getName().getLocalPart();
                if (name.equalsIgnoreCase(endElement)) {
                    return filters;
                }
            }
        }
        return filters;
    }

    /**
     * 解析repeat = 3的标签
     *
     * @param reader
     * @param elements
     * @param xml
     * @param endElement
     * @return
     * @throws Exception
     */
    public static String parseTreesLabels(XMLStreamReader reader, Map<String, Element> elements, String xml, String endElement) throws Exception {
        String name = null;
        String message = xml == null ? "" : xml;
        String key = null;
        String label = null;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                key = reader.getAttributeValue(0);
                name = reader.getName().getLocalPart();
                if ("0".equals(key)) {
                    elements.get(endElement).setChildName(name);
                    label = reader.getAttributeValue(2);
                    message = message.concat(getLabel(label, true));
                    key = reader.getAttributeValue(1);
                    if ("1".equals(key)) {
                        message = parseSingleLabels(reader, elements.get(endElement).getSubElement(), message, name)
                                .concat(getLabel(label, false));
                    }
                    label = null;
                }
            }
            if (type == XMLStreamConstants.END_ELEMENT) {
                name = reader.getName().getLocalPart();
                if (name.equalsIgnoreCase(endElement)) {
                    return message;
                }
            }
        }
        return message;
    }

    /**
     * 解析repeat = 2 的标签
     *
     * @param reader
     * @param elements
     * @param xml
     * @param endElement
     * @return
     * @throws Exception
     */
    public static String parseDoubleLabels(XMLStreamReader reader, Map<String, Element> elements, String xml, String endElement) throws Exception {
        String name = null;
        String srcName = null;
        String message = xml == null ? "" : xml;
        Element e = null;
        String key = null;
        String label = null;

        String vstrict = null;
        String vdefalt = null;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                key = reader.getAttributeValue(0);
                if ("1".equals(key)) {
                    srcName = reader.getName().getLocalPart();
                    message = message.concat(getLabel(srcName, true)).concat(getLabel(srcName, false));

                    vdefalt = reader.getAttributeValue(null, "default");
                    vstrict = reader.getAttributeValue(null, "strict");
                    name = reader.getElementText();
                    if (elements.containsKey(name)) {
                        elements.get(name).addSrcName(srcName, vdefalt, vstrict);
                    } else {
                        e = new Element();
                        e.setLeaf(true);
                        e.addSrcName(srcName, vdefalt, vstrict);
                        e.setName(name);
                        elements.put(e.getName(), e);
                    }
                } else if ("0".equals(key)) {
                    key = reader.getAttributeValue(1);
                    name = reader.getName().getLocalPart();
                    label = reader.getAttributeValue(2);
                    message = message.concat(getLabel(label, true));
                    if ("3".equals(key)) {
                        e = new Element();
                        e.setName(name);
                        e.setSubElement(new HashMap<String, Element>());
                        elements.put(e.getName(), e);
                        message = parseTreesLabels(reader, elements, message, name).
                                concat(getLabel(label, false));
                    }
                    label = null;
                }
            }
            if (type == XMLStreamConstants.END_ELEMENT) {
                name = reader.getName().getLocalPart();
                if (name.equalsIgnoreCase(endElement)) {
                    return message;
                }
            }
        }
        return message;
    }

    /**
     * 解析repeat = 1的标签
     *
     * @param reader
     * @param elements
     * @param xml
     * @param endElement
     * @return
     * @throws Exception
     */
    public static String parseSingleLabels(XMLStreamReader reader, Map<String, Element> elements, String xml, String endElement) throws Exception {
        String name = null;
        String srcName = null;
        String message = xml == null ? "" : xml;
        String vstrict = null;
        String vdefalt = null;
        Element e = null;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                srcName = reader.getName().getLocalPart();
                vdefalt = reader.getAttributeValue(null, "default");
                vstrict = reader.getAttributeValue(null, "strict");
                message = message.concat(getLabel(srcName, true)).concat(getLabel(srcName, false));
                name = reader.getElementText();

                if (elements.containsKey(name)) {
                    elements.get(name).addSrcName(srcName, vdefalt, vstrict);
                } else {
                    e = new Element();
                    e.setLeaf(true);
                    e.addSrcName(srcName, vdefalt, vstrict);

                    e.setName(name);
                    elements.put(e.getName(), e);
                }
            }
            if (type == XMLStreamConstants.END_ELEMENT) {
                name = reader.getName().getLocalPart();
                if (name.equalsIgnoreCase(endElement)) {
                    return message;
                }
            }
        }
        return message;
    }

    /**
     * 根据名字获取标签
     *
     * @param name
     * @param start
     * @return
     */
    public static String getLabel(String name, boolean start) {
        if (name == null || "".equals(name.trim())) {
            return "";
        }
        if (start) {
            return "<".concat(name).concat(">");
        }
        return "</".concat(name).concat(">\r\n");
    }

    /**
     * 检查标签是否为重复性标签
     *
     * @param label
     * @param es
     * @return
     */
    public static boolean isRepeatLabel(String label, Map<String, Element> es) {
        if (label == null || "".equals(label.trim())) {
            return false;
        }
        Element e = es.get(label);
        if (e != null && e.getChildName() != null) {
            return true;
        }
        return false;
    }

    /**
     * 检查标签是否在目的标签中
     *
     * @param label
     * @param maps
     * @return
     */
    public static boolean checkeExistsLabelInModels(String label, Map<String, Element> maps) {
        if (label == null || "".equals(label.trim())) {
            return false;
        }
        if (maps.containsKey(label) && maps.get(label).getChildName() == null) {
            return true;
        }
        return false;
    }

    /**
     * 根据源报文标签名字获取目标标签名字
     *
     * @param name
     * @param maps
     * @return
     */
    public static Map<String, ValuePair<String, String>> getSingleLabelTargetName(String name, Map<String, Element> maps) {
        return maps.get(name).getSrcNameValuePair();
    }

    /**
     * 根据标签名和值生成完成的标签
     *
     * @param labelName
     * @param text
     * @return
     */
    public static String generatedClosedLabel(String labelName, String text, boolean cdata) {
        if (labelName == null || "".equals(labelName.trim())) {
            return "";
        }
        if (text == null) {
            text = "";
        }
        text = getElementContentWithCDATA(text, cdata);
        text = getLabel(labelName, true).concat(text).concat(getLabel(labelName, false));
        return text;
    }

    public static String generatedClosedLabel(String labelName, String text) {
        return generatedClosedLabel(labelName, text, false);
    }

    public static boolean isEnclosedCDATA() {
        return Global.CDATA_ADD == 0 ? false : true;
    }

    /**
     * 将内容中的特殊字符进行转义
     *
     * @param text < > & "
     * @return
     */
    public static String getTextFromSpecialChars(String text) {
        return text.replace(Global.SP_AND_CHAR, Global.SP_AND)
                .replace(Global.SP_BRACES_LEFT_CHAR, Global.SP_BRACES_LEFT)
                .replace(Global.SP_BRACES_RIGHT_CHAR, Global.SP_BRACES_RIGHT)
                .replace(Global.SP_QUOTS_CHAR, Global.SP_QUOTS);
    }

    /**
     * 处理<CDATA[[ ]]> 问题
     *
     * @param reader
     * @return
     */
    public static String getElementContentFromCDATA(XMLStreamReader reader) {
        String value = null;
        try {
            value = reader.getElementText();
            if (value != null && value.startsWith("<![CDATA[") && value.endsWith("]]>")) {
                value = value.substring(8, value.length() - 3);
            }
            return value;
        } catch (XMLStreamException e) {
            log.error("Get Element Content Error!", e);
        }
        return "";
    }

    /**
     * 处理<CDATA[[ ]]> 问题
     *
     * @param reader
     * @return
     */
    public static String getElementContentFromCDATA(String value) {
        if (value != null && value.startsWith(Global.CDATA_START) && value.endsWith(Global.CDATA_END)) {
            value = value.substring(Global.CDATA_START.length(), value.length() - Global.CDATA_END.length());
        }
        return value;
    }

    /**
     * 添加<CDATA[[ ]]>
     * 如果需要添加
     *
     * @param reader
     * @return
     */
    public static String getElementContentWithCDATA(String content, boolean cdata) {
        if (content == null) {
            content = "";
        }
        if (!cdata) {
            content = getElementContentFromCDATA(content);
            return getTextFromSpecialChars(content);
        }
        if (content.startsWith(Global.CDATA_START) && content.endsWith(Global.CDATA_END)) {
            return content;
        }
        return Global.CDATA_START.concat(content).concat(Global.CDATA_END);
    }

    /**
     * 从字符串数组中挑选出第一个不是空的作为返回值 不为空： 长度 > 0. 如果参数为null那么 数组的表现形式就是 字符串"null"。
     *
     * @param args
     * @return
     */
    public static String pickCorrectedValue(String... args) {
        if (args.length <= 0) {
            return "";
        }
        String v = null;
        for (int i = 0; i < args.length; i++) {
            v = args[i];
            if (v != null && !"".equals(v)) {
                return v;
            }
        }
        return "";
    }

    public static void main(String argsp[]) throws IOException {
//		System.out.println(Utils.getConfigs().get("t"));
        System.out.println("_AdapTor.xml".matches("^.*_[aA][dD][aA][pP][tT][oO][rR]\\.xml$"));
        System.out.println(pickCorrectedValue(null, "", "", "wef"));
    }

    /**
     * 根据路径将文件读取为字符串
     *
     * @param path
     * @return
     */
    public static String readFromFile(File f) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(f));
            StringBuilder stringBuilder = new StringBuilder();
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据路径将文件读取为字符串
     *
     * @param path
     * @return
     */
    public static String readFromFile(String path) {
        try {
            File f = new File(path);
            if (!f.exists() || !f.isFile()) {
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    f));
            StringBuilder stringBuilder = new StringBuilder();
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取报文类型配置参数
     *
     * @return
     */
    public static String getMsgTypeConfigs(String type) {
        if (MTC != null && type != null) {
            return MTC.get(type.trim().toUpperCase());
        }
        return null;
    }

    public static String getProperty(String key, String defalut) {
        if (p != null && !p.isEmpty()) {
            return (String) p.getProperty(key, defalut);
        }
        log.error("##Config File[ Adaptors.properties] isn't contains key { " + key + " } . Default is used : " + defalut);
        return defalut;
    }

//	public static void writeMessageAppendable(String path,String fileName,String content,Date lastModified,boolean checkedPath,boolean append) throws Exception {
//		if(path==null||"".equals(path)) path = null;
//		if(fileName==null) return ;
//		if(!path.endsWith(File.separator)) fileName = path.concat(File.separator).concat(fileName);
//		else fileName = path.concat(fileName);
//		if(content==null) content = "";
//		File dir = new File(path);
//		if(!existDirOrFile(dir,checkedPath)) {
//			log.error("## Error: File [ "+dir.getAbsolutePath()+" ] wasn't exist !");
//			return;
//		}
////		String f = new String(fileName.getBytes("utf-8"));
//		dir = new File(fileName);
//		FileChannel fc = new RandomAccessFile(dir,"rws").getChannel();
//		FileLock lock = null;
//		try {
//			lock = fc.tryLock();
//			if(append) {
//				fc.write(ByteBuffer.wrap(content.getBytes("utf-8")), fc.size());
//			} else {
//				fc.truncate(0);
//				fc.write(ByteBuffer.wrap(content.getBytes("utf-8")), fc.position());
//			}
//			//清空文件
//			//向文件写入新内容
//			//向文件追加
////	    		fc.write(ByteBuffer.wrap(content.getBytes()), fc.size());
//		}catch(Exception e) {
//			log.error("File [ "+fileName+" ] keeped Error !",e);
//		}finally {
//			lock.release();
//			fc.close();
//			if(lastModified!=null) dir.setLastModified(lastModified.getTime());
//			fc = null;
//			dir = null;
//			lock = null;
//			log.debug("Write File [ "+fileName+": "+content+" ] ");
//		}
//	}
    /**
     * 检测目录/文件是否存在
     *
     * @param dir
     * @param made ： 如果文件/目录不存在是否要创建
     * @return
     */
    public static boolean existDirOrFile(File dir, boolean made) {
        if (!dir.exists()) {
            if (!made) {
                return false;
            }
            log.debug("*************************  Make dir : " + dir.getAbsolutePath() + "  *****************************");
            dir.mkdirs();
            return true;
        }
        return true;
    }
}
