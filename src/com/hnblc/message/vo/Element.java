package com.hnblc.message.vo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hnblc.message.plugins.MessageFilter;

public class Element implements Cloneable{

	private String name;
	private String childName;
	private boolean isLeaf;
	private Map<String,Element> subElement = null;
	private List<MessageFilter> filters = null;
	
	//目标标签对应的默认值和固定值
	private Map<String, ValuePair<String,String>> srcNameValuePair = null;

	public List<MessageFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<MessageFilter> filters) {
		this.filters = filters;
	}
	
	public Map<String, ValuePair<String, String>> getSrcNameValuePair() {
		return srcNameValuePair;
	}

	public void addSrcName(String srcName, String defalt, String strict) {
		if(this.srcNameValuePair==null) this.srcNameValuePair = new HashMap<String,ValuePair<String, String>>();
		if(srcName==null||"".equals(srcName.trim())) return;
		this.srcNameValuePair.put(srcName,new ValuePair<String,String>(defalt,strict));
	}
	public Element(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Map<String, Element> getSubElement() {
		return subElement;
	}
	public void setSubElement(Map<String, Element> subElement) {
		this.subElement = subElement;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public String toString() {
		return "{name:"+this.name+",src:"+this.srcNameValuePair+":{"+this.subElement+"} , filter: "+this.filters +" }; ";
	}

	public Element getCopy() {
		try {
			return (Element) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return new Element();
	}
	
//	public static void main(String[] args) {
//		Map<String, Element> ms = new HashMap<String, Element>();
//		ms.put("ss", new Element());
//		ms.get("ss").setName("ss");
//		Element e = new Element();
//		e.setSubElement(ms);
//		e.setName("aa");
//		System.out.println(e.toString());
//		System.out.println(e.getCopy());
//		
//	}
}
