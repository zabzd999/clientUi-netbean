package com.hnblc.message.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class XmlStreamFilterImpl implements StreamFilter {

	private List<String> elements = new ArrayList<String>();
	
	public XmlStreamFilterImpl(List<String> elements) {
		if(elements!=null&&!elements.isEmpty()) this.elements.addAll(elements);
	}
	@Override
	public boolean accept(XMLStreamReader reader) {
		QName q = null;
		String name = null;
		try {
			int type = reader.getEventType();
			if(type==XMLStreamConstants.START_ELEMENT) {
				q = reader.getName();
				name = q.getLocalPart();
				if(elements.contains(name)) return true;
			}
		} finally {
			q = null;
			name = null;
		}
		return false;
	}

}
