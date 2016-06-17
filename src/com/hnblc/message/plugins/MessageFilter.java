package com.hnblc.message.plugins;

/**
 * 用户自定义报文处理逻辑
 * @author Administrator
 *
 */
public interface MessageFilter {
	
	/**
	 * 处理报文中不规范的内容或标签
	 * @param srcMsgStr   原始报文
	 * @param newMsgStr   新报文
	 * @return
	 * @throws Exception
	 */
	public String filter(String srcMsgStr, String newMsgStr) throws Exception;
	
	public void setProperty(String key, Object obj);
}
