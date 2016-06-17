package com.hnblc.sign.entity;


public class UkeyResult {
	private int flag=0;//标记调用设备是否成功 1 表示成功 0表示失败 -1 表示异常
	private String detail;//描述设备返回信息
	private byte[] byteResult;//记录返回byte数组数据
	private String result;//描述设备返回结果
	private int len=0;//接收设备返回参数的长度
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public byte[] getByteResult() {
		return byteResult;
	}
	public void setByteResult(byte[] byteResult) {
		this.byteResult = byteResult;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}

}
