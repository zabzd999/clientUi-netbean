package com.hnblc.message.utils;


public class Global {

	public static final String LABEL_TO_BE_REPLACE = "blank";
	public static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
	public static final String SP_BRACES_LEFT = "&lt;"; //<
	public static final String SP_BRACES_LEFT_CHAR = "<"; //<
	public static final String SP_BRACES_RIGHT = "&gt;"; //>
	public static final String SP_BRACES_RIGHT_CHAR = ">"; //>
	public static final String SP_AND = "&amp;"; //&
	public static final String SP_AND_CHAR = "&"; //&
	public static final String SP_QUOTS = "&quot;"; //"
	public static final String SP_QUOTS_CHAR = "\""; //"
	public static final String SP_COPYRIGHT = "&copy;"; //©
	public static final String SP_COPYRIGHT_CHAR = "©"; //©
	public static final String DEFAULT_CHARATERSET = "UTF-8";
	
	public static int CDATA_ADD = 0;   //0 默认不添加   1添加 <CDATA[[ ]]>
	public static String CDATA_START = "<CDATA[[";
	public static String CDATA_END ="]]>";
	
}
