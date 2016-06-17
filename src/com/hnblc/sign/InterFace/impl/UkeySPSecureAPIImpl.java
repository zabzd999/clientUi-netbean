package com.hnblc.sign.InterFace.impl;

import com.hnblc.sign.InterFace.UkeySPSecureAPI;
import com.sun.jna.ptr.IntByReference;

public class UkeySPSecureAPIImpl implements UkeySPSecureAPI{

	/*
	    * 取卡签名证书  0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1输出参证书内容，以\0结束   分配足够内存  返回信息格式以||隔开
	    *   2输入输出参数 证书内容长度长度  为实际的signcertcontentlen长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	@Override
	public int SpcGetSignCert(byte[] szcert, IntByReference szcertlen) {
		return UkeySPSecureAPI.instance.SpcGetSignCert(szcert, szcertlen);
	}
	 /*
	    * 打开U可以设备  0表示打开成功 负责表示打开失败 失败后调用获获取错误信息
	    * */
	@Override
	public int SpcInitEnvEx() {
		return UkeySPSecureAPI.instance.SpcInitEnvEx();
	}
	 /*
	    * 读取卡内随机数 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 随机数   2 随机数长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	@Override
	public int SpcGetRandom(byte[] random, int randomlen) {
		return UkeySPSecureAPI.instance.SpcGetRandom(random, randomlen);
	}
	/*
	   * 取证书信息
	   * 取证书信息；若证书不为NULL且证书长度不为0，则取当前证书的信息，之前不必打开卡；若证书为NULL或证书长度为0，则取当前加密设备的信息，之前必须打开卡
	   * 参数： 1 输入  DER编码格式的证书，若为NULL，则表示取当前加密设备的证书信息
	   *     2 输入 证书长度，若为0，则表示取当前加密设备的证书信息
	   *     3 输入 证书信息索引项（1 证书号，27 申请者名称，54 证书类型，69 证书号码，53 社会保险号，70 IC卡号，52 单位名称，72 单位类别，71 单位代码， 57 证书受理点，21 证书有效开始时间，22 证书有效结束时间）
	   *     4 输出  证书项的值，必须分配足够的空间，至少100字节
	   *     5 输入输出  证书项长度，输入时必须等于outcertData实际开辟的空间大小
	   * */
	@Override
	public int SpcGetCertInfo(byte[] szcert, int szcertlen, int index,
			byte[] szout, IntByReference szoutlen) {
		return UkeySPSecureAPI.instance.SpcGetCertInfo(szcert, szcertlen, index, szout, szoutlen);
	}
	 /*
	    * 关闭加密设备 0 表示成功 否则表示失败 失败后调用获获取错误信息
	    * */
	@Override
	public int SpcClearEnv() {
		return UkeySPSecureAPI.instance.SpcClearEnv();
	}
	 /*    * 
	    * 验证用户口令 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 密码   2 密码长度
	    * 
	    * 注意事项： 1、验证用户口令之前 必须先打开设备
	    * */
	@Override
	public int SpcVerifyPIN(String password, int pwdlen) {
		return UkeySPSecureAPI.instance.SpcVerifyPIN(password, pwdlen);
	}
	/*    * 
	    * 修改用户口令 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 原始密码   2 原始密码长度    3 新密码   4 新密码长度
	    * 
	    * 注意事项：1、操作该方法之前 必须先打开设备
	    * */
	@Override
	public int SpcChangePIN(String orgpassword, int orgpwdlen,
			String newpassword, int newpwdlen) {
		return UkeySPSecureAPI.instance.SpcChangePIN(orgpassword, orgpwdlen, newpassword, newpwdlen);
	}
	 /*
	   * 对称算法加密，支持DES/3DES、CDEA、IDEA、SSF33、SM1，其中SSF33和SM1调用之前需调用InitEnv()。
	   * 
	   * 
	   * * 参数 ：1输入参数 对称算法标识	DES --- 0x10
								3DES --- 0x20
								CDEA --- 0x30
								SSF33 --- 0x31
								SM1 --- 0x32
								IDEA --- 0x40

	   *   2 输入参数  加密密钥
	   *   3  输入参数  明文
	   *   4  输入参数  明文长度
	   *   5  输出参数  密文
	   *   6   输入输出参数  密文长度
	   * */
	@Override
	public int SpcSymEncrypt(int flag, byte[] orgData,
			int orgDatalen, byte[] szout, IntByReference szoutlen, byte[] key) {
		return UkeySPSecureAPI.instance.SpcSymEncrypt(flag, orgData, orgDatalen, szout, szoutlen, key);
	}
	 /*
	   * 取错误信息，调用该函数之前无需调用打开卡函数
	   * 参数： 1 输入参数  错误代码
	   * */
	@Override
	public  String SpcGetErrMsg(int errorcode) {
		return UkeySPSecureAPI.instance.SpcGetErrMsg(errorcode);
	}
	 /*
	    * 取卡加密证书  0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1输出参证书内容，以\0结束   分配足够内存  返回信息格式以||隔开
	    *   2输入输出参数 证书内容长度长度  为实际的encryptcertcontentlen长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	@Override
	public int SpcGetEnvCert(byte[] encryptcertcontent,
			IntByReference encryptcertcontentlen) {
		return UkeySPSecureAPI.instance.SpcGetEnvCert(encryptcertcontent, encryptcertcontentlen);
	}
	/*
	   * RSA加密。
	   * 
	   * 
	   * * 参数 ：1输入参数 DER编码格式的证书
	   *   2 输入参数  证书长度
	   *   3  输入参数  明文
	   *   4  输入参数  明文长度
	   *   5  输出参数  密文
	   *   6   输入输出参数  密文长度
	   * */

	@Override
	public int SpcRSAEncrypt(byte[] certcontent, int certcontentlen,
			String cipherData, int cipherDatalen, byte[] outopenData,
			IntByReference outopenDatalen) {
		return UkeySPSecureAPI.instance.SpcRSAEncrypt(certcontent, certcontentlen, cipherData, cipherDatalen, outopenData, outopenDatalen);
	}
	 /*
	   * 对称算法解密，支持DES/3DES、CDEA、IDEA、SSF33、SM1，其中SSF33和SM1调用之前需调用InitEnv()。
	   * 
	   * 
	   * * 参数 ：1输入参数 对称算法标识	DES --- 0x10
								3DES --- 0x20
								CDEA --- 0x30
								SSF33 --- 0x31
								SM1 --- 0x32
								IDEA --- 0x40

	   *   2 输入参数  加密密钥
	   *   3  输入参数  明文
	   *   4  输入参数  明文长度
	   *   5  输出参数  密文
	   *   6   输入输出参数  密文长度
	   * */
	@Override
	public int SpcSymDecrypt(int flag, byte[] cipherData,
			int cipherDatalen, byte[] outopenData, IntByReference outopenDatalen, byte[] key) {
		return UkeySPSecureAPI.instance.SpcSymDecrypt(flag, cipherData, cipherDatalen, outopenData, outopenDatalen, key);
	}
	 /*
	   * RSA解密。调用该函数前必须打开卡并验证口令
	   * 
	   * 
	   * * 参数 ：1输入参数 密文
	   *   2 输入参数 密文长度
	   *   3  输入参数  明文，至少分配与密文相同的空间
	   *   4   输入输出参数  明文长度，输入时必须等于openData实际开辟的空间大小
	   * */
	@Override
	public int SpcRSADecrypt(byte[] ciphercontent, int ciphercontentlent,
			byte[] openData, IntByReference outopenDatalen) {
		return UkeySPSecureAPI.instance.SpcRSADecrypt(ciphercontent, ciphercontentlent, openData, outopenDatalen);
	}
	/*
	    * 用加密设备对数据签名  0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 签名算法为RSA+SHA1
	    * 
	    * 参数 ：1输入参数 待签名的数据 
	    *   2 输入参数 输入待签名数据的长度
	    *   3 输出参数  签名数据，未PEM编码，至少分配128字节
	    *   4 输入输出参数  签名数据长度，应大于128个字节，输入时应等于outsignData实际分配的空间大小
	    * 
	    *  注意事项：1、操作该方法之前 必须调用SpcInitEnvEx()和SpcVerifyPIN()函数
	    * */
	@Override
	public int SpcSignData(String signData, int signDatalen,
			byte[] outsignData, IntByReference outsignDatalen) {
		return UkeySPSecureAPI.instance.SpcSignData(signData, signDatalen, outsignData, outsignDatalen);
	}
	/*
	    * 获取证书号   0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1输出参数 证书号  证书号，以\0结束   2输入输出参数  证书号长度  为实际的cernolen长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	@Override
	public int SpcGetCertNo(byte[] cerno, IntByReference cernolen) {
		return UkeySPSecureAPI.instance.SpcGetCertNo(cerno, cernolen);
	}
	 /*
	    * 验证数字签名，签名算法为RSA+SHA1， 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 
	    * 参数 ：1输入参数 证书内容，必须为der编码格式（如原证书是pem编码则必须解码后传入）
	    *   2 输入参数  证书长度
	    *   3  输入参数  原始数据
	    *   4  输入参数  原始数据长度
	    *   5  输入参数  签名数据，未PEM编码
	    *   6   输入参数  签名数据长度，应等于128
	    * 
	    *  注意事项：1、操作该方法之前 必须调用之前不必调用SpcInitEnvEx ()
	    * */
	@Override
	public int SpcVerifySignData(byte[] certcontent, int certcontentlen,
			String orgDatacontent, int orgDatacontentlen, String signData,
			int signDatalen) {
		return UkeySPSecureAPI.instance.SpcVerifySignData(certcontent, certcontentlen, orgDatacontent, orgDatacontentlen, signData, signDatalen);
	}
	@Override
	public int SpcGetCardID(byte[] cardid, IntByReference cardidlen) {
		return UkeySPSecureAPI.instance.SpcGetCardID(cardid, cardidlen);
	}

}
