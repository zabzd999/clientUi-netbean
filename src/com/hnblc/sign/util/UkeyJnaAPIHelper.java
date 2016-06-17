package com.hnblc.sign.util;

import com.hnblc.sign.InterFace.UkeySPSecureAPI;
import com.hnblc.sign.InterFace.impl.UkeySPSecureAPIImpl;
import com.hnblc.sign.entity.UkeyResult;
import com.sun.jna.ptr.IntByReference;

public class UkeyJnaAPIHelper {
	UkeySPSecureAPI ukeyAPI;
	public UkeyJnaAPIHelper()
	{
		ukeyAPI=new UkeySPSecureAPIImpl(); 
	}
	 /*
	    * 打开Ukey设备  1表示打开成功
	    * */
	public UkeyResult SpcInitEnvEx() {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcInitEnvEx=ukeyAPI.SpcInitEnvEx();
			if(0==spcInitEnvEx)
			{
				ur.setFlag(1);
				ur.setDetail("打开设备成功");
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcInitEnvEx)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	/*
	    * 取卡签名证书  0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1输出参证书内容，以\0结束   分配足够内存  返回信息格式以||隔开
	    *   2输入输出参数 证书内容长度长度  为实际的signcertcontentlen长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	public UkeyResult SpcGetSignCert(byte[] szcert, IntByReference szcertlen) {
		
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetSignCert=ukeyAPI.SpcGetSignCert(szcert, szcertlen);
			if(0==spcGetSignCert)
			{
				ur.setFlag(1);
				ur.setDetail("取卡签名证书成功");
				ur.setByteResult(szcert);
				ur.setLen(szcertlen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetSignCert)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	/*
	    * 读取卡内随机数 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 随机数   2 随机数长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	public UkeyResult SpcGetRandom(byte[] random, int randomlen) {

		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetRandom=ukeyAPI.SpcGetRandom(random, randomlen);
			if(0==spcGetRandom)
			{
				ur.setFlag(1);
				ur.setDetail("读取卡内随机数成功");
				ur.setByteResult(random);
				ur.setLen(randomlen);
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetRandom)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
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
	public UkeyResult SpcGetCertInfo(byte[] szcert, int szcertlen, int index,
			byte[] szout, IntByReference szoutlen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetCertInfo=ukeyAPI.SpcGetCertInfo(szcert, szcertlen, index, szout, szoutlen);
			if(0==spcGetCertInfo)
			{
				ur.setFlag(1);
				ur.setDetail("取证书信息成功");
				ur.setByteResult(szout);
				ur.setLen(szoutlen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetCertInfo)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	 /*
	    * 关闭加密设备 0 表示成功 否则表示失败 失败后调用获获取错误信息
	    * */
	public UkeyResult SpcClearEnv() {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetCertInfo=ukeyAPI.SpcClearEnv();
			if(0==spcGetCertInfo)
			{
				ur.setFlag(1);
				ur.setDetail("关闭加密设备成功");
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetCertInfo)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	 /*    * 
	    * 验证用户口令 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 密码   2 密码长度
	    * 
	    * 注意事项： 1、验证用户口令之前 必须先打开设备
	    * */
	public UkeyResult  SpcVerifyPIN(String password, int pwdlen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcVerifyPIN=ukeyAPI.SpcVerifyPIN(password, pwdlen);
			if(0==spcVerifyPIN)
			{
				ur.setFlag(1);
				ur.setDetail("验证用户口令成功");
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcVerifyPIN)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	/*    * 
	    * 修改用户口令 0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 原始密码   2 原始密码长度    3 新密码   4 新密码长度
	    * 
	    * 注意事项：1、操作该方法之前 必须先打开设备
	    * */
	public UkeyResult SpcChangePIN(String orgpassword, int orgpwdlen,
			String newpassword, int newpwdlen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcChangePIN=ukeyAPI.SpcChangePIN(orgpassword, orgpwdlen, newpassword, newpwdlen);
			if(0==spcChangePIN)
			{
				ur.setFlag(1);
				ur.setDetail("修改用户口令成功");
				ur.setResult("新密码为："+newpassword);
				
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcChangePIN)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	 /*
	   * 取错误信息，调用该函数之前无需调用打开卡函数
	   * 参数： 1 输入参数  错误代码
	   * */
	public String  SpcGetErrMsg(int errorcode) {
		return ukeyAPI.SpcGetErrMsg(errorcode);
	}
	 /*
	    * 取卡加密证书  0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1输出参证书内容，以\0结束   分配足够内存  返回信息格式以||隔开
	    *   2输入输出参数 证书内容长度长度  为实际的encryptcertcontentlen长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	public UkeyResult SpcGetEnvCert(byte[] encryptcertcontent,
			IntByReference encryptcertcontentlen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetEnvCert=ukeyAPI.SpcGetEnvCert(encryptcertcontent, encryptcertcontentlen);
			if(0==spcGetEnvCert)
			{
				ur.setFlag(1);
				ur.setDetail("取卡加密证书成功");
				ur.setByteResult(encryptcertcontent);
				ur.setLen(encryptcertcontentlen.getValue());
				
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetEnvCert)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
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

	public UkeyResult SpcRSAEncrypt(byte[] certcontent, int certcontentlen,
			String cipherData, int cipherDatalen, byte[] outopenData,
			IntByReference outopenDatalen) {
		
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcRSAEncrypt=ukeyAPI.SpcRSAEncrypt(certcontent, certcontentlen, cipherData, cipherDatalen, outopenData, outopenDatalen);
			if(0==spcRSAEncrypt)
			{
				ur.setFlag(1);
				ur.setDetail("RSA加密成功");
				
				
				int k=0;
				for(int i=outopenData.length-1;i>0;i--)
				{
					if(0==outopenData[i])
					{
						continue;
					}
					else
					{
						k=i+1;
						break;
					}
				}
				byte[] result=new byte[k];
				for(int n=0;n<k;n++)
				{
					result[n]=outopenData[n];
				}
				ur.setByteResult(result);
				ur.setLen(outopenDatalen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcRSAEncrypt)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	 /*
	   * RSA解密。调用该函数前必须打开卡并验证口令
	   * 
	   * 
	   * * 参数 ：1输入参数 密文
	   *   2 输入参数 密文长度
	   *   3  输出参数  明文，至少分配与密文相同的空间
	   *   4   输入输出参数  明文长度，输入时必须等于openData实际开辟的空间大小
	   * */
	public UkeyResult SpcRSADecrypt(byte[] ciphercontent, int ciphercontentlent,
			byte[] openData, IntByReference outopenDatalen) 
	{
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcRSADecrypt=ukeyAPI.SpcRSADecrypt(ciphercontent, ciphercontentlent, openData, outopenDatalen);
			if(0==spcRSADecrypt)
			{
				ur.setFlag(1);
				ur.setDetail("RSA解密成功");
				
				int k=0;
				for(int i=openData.length-1;i>0;i--)
				{
					if(0==openData[i])
					{
						continue;
					}
					else
					{
						k=i+1;
						break;
					}
				}
				byte[] result=new byte[k];
				for(int n=0;n<k;n++)
				{
					result[n]=openData[n];
				}
				ur.setByteResult(result);
				ur.setLen(outopenDatalen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcRSADecrypt)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	 /*
	    * 获取卡号   0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1 输出参数 卡号  卡号，以\0结束   2 输入输出参数 长度  为实际的cardno长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	public UkeyResult SpcGetCardID(byte[] cardid,IntByReference cardidlen)
	{
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetCardID=ukeyAPI.SpcGetCardID(cardid, cardidlen);
			if(0==spcGetCardID)
			{
				ur.setFlag(1);
				ur.setDetail("获取卡号成功");
				ur.setByteResult(cardid);
				ur.setLen(cardidlen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetCardID)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
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
	public UkeyResult SpcSignData(String signData, int signDatalen,
			byte[] outsignData, IntByReference outsignDatalen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcSignData=ukeyAPI.SpcSignData(signData, signDatalen, outsignData, outsignDatalen);
			if(0==spcSignData)
			{
				ur.setFlag(1);
				ur.setDetail("数据签名成功");
				ur.setByteResult(outsignData);
				ur.setLen(outsignDatalen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcSignData)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	/*
	    * 获取证书号   0表示成功 否则表示失败  失败后调用获获取错误信息
	    * 
	    * 参数 ：1输出参数 证书号  证书号，以\0结束   2输入输出参数  证书号长度  为实际的cernolen长度   
	    * 
	    *  注意事项：1、操作该方法之前 必须先打开设备
	    * */
	public UkeyResult SpcGetCertNo(byte[] cerno, IntByReference cernolen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcGetCertNo=ukeyAPI.SpcGetCertNo(cerno, cernolen);
			if(0==spcGetCertNo)
			{
				ur.setFlag(1);
				ur.setDetail("获取证书号成功");
				ur.setByteResult(cerno);
				ur.setLen(cernolen.getValue());
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcGetCertNo)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
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
	public UkeyResult SpcVerifySignData(byte[] certcontent, int certcontentlen,
			String orgDatacontent, int orgDatacontentlen, String signData,
			int signDatalen) {
		UkeyResult ur=new UkeyResult();
		try
		{
			int spcVerifySignData=ukeyAPI.SpcVerifySignData(certcontent, certcontentlen, orgDatacontent, orgDatacontentlen, signData, signDatalen);
			if(0==spcVerifySignData)
			{
				ur.setFlag(1);
				ur.setDetail("验证数字签名成功");
			}
			else
			{
				ur.setFlag(0);
				ur.setDetail(new String(ukeyAPI.SpcGetErrMsg(spcVerifySignData)));
			}
		}
		catch(Exception ex)
		{
			ur.setFlag(-1);
			ur.setDetail("读取设备信息异常，请检查设备是否驱动成功");
		}
		return ur;
	}
	/*
	 * 3DES解密,传入加密后的 byte 数组  以及秘钥
	 * */
	public  byte[] SpcSymDecrypt3DES(byte[] encryDate,String keys)
	{
		
		 byte[] key=keys.getBytes();//加密Key 24 位
		 
		 byte[] orgdata=encryDate;//等待加密的数据	 
		 byte[] szout=new byte[8];//八位加密输出数据
		 IntByReference szoutblen=new IntByReference(szout.length);//长度
		 int k=0;//判断需要加密数据  拆分的数组数量
		 int begin=0;//用于从待加密数据中截取的位置
		 int count=8;//用于从待加密数据中取出的长度

		
		 if(orgdata.length%count==0)
		 {
			 k=orgdata.length/count; 
		 }
		 else
		 {
			 k=orgdata.length/count+1; 
		 }
		 byte[] lastentrydata=new byte[k*count];
		 if(orgdata.length%count==0)
		 {
			 k=orgdata.length/count; 
			 for(int i=0;i<k;i++)
			 {
				 begin=i*count;
				 byte[] encrydata=subBytes(orgdata,begin,count);
				 int result=ukeyAPI.SpcSymDecrypt(0x20, encrydata, count, szout, szoutblen, key);
				 if(result==0)
				 {
					 for(int m=0;m<szout.length;m++)
					 {
						 lastentrydata[i*count+m]=szout[m]; 
					 }
				 }
			 }
		 }
		 else
		 {
			 k=orgdata.length/count+1; 
			 for(int i=0;i<k-1;i++)
			 {
				 begin=i*count;
				 byte[] encrydata=subBytes(orgdata,begin,count);
				 int result=ukeyAPI.SpcSymDecrypt(0x20, encrydata, count, szout, szoutblen, key);
				 if(result==0)
				 {
					 for(int m=0;m<szout.length;m++)
					 {
						 lastentrydata[i*count+m]=szout[m]; 
					 }
				 }
			 }
			 byte[] lessb=subBytes(orgdata,(k-1)*count,orgdata.length%8);
			 int result=ukeyAPI.SpcSymDecrypt(0x20, lessb, count, szout, szoutblen, key);
			 if(result==0)
			 {
				 for(int m=0;m<orgdata.length/8;m++)
				 {
					 lastentrydata[(k-1)*count+m]=szout[m]; 
				 } 
			 }
		 }
		 //解密后对去除补位数据 0
		 int s=0;
		 for(int n=lastentrydata.length-1;n>lastentrydata.length-8;n--)
		 {
			 if(0==lastentrydata[n])
			 {
				 continue;
			 }
			 else
			 {
				 s=n+1;
				 break;
			 }
		 }
		 byte[] result=new byte[s];
		 for(int a=0;a<s;a++)
		 {
			 result[a]=lastentrydata[a];
		 }
		 lastentrydata=null;
		 orgdata=null;
		 return result;
	}
	/*
	 * 3DES加密，传入需要加密的数据，和秘钥   返回加密后的byte数组
	 * */
	public  byte[] SpcSymEncrypt3DES(String orgData,String keys)
	{
		 byte[] key=keys.getBytes();//加密Key 24 位
		 
		 byte[] orgdata=orgData.getBytes();//等待加密的数据	 
		 byte[] szout=new byte[8];//八位加密输出数据
		 IntByReference szoutblen=new IntByReference(szout.length);//长度
		 int k=0;//判断需要加密数据  拆分的数组数量
		 int begin=0;//用于从待加密数据中截取的位置
		 int count=8;//用于从待加密数据中取出的长度

		 //判断待加密的数据是否为8的整数倍
		 if(orgdata.length%count==0)
		 {
			 k=orgdata.length/count; 
		 }
		 else
		 {
			 k=orgdata.length/count+1; 
		 }
		 byte[] lastentrydata=new byte[k*count];
		 //整数倍加密算法
		 if(orgdata.length%count==0)
		 {
			 k=orgdata.length/count; 
			 for(int i=0;i<k;i++)
			 {
				 begin=i*count;
				 byte[] encrydata=subBytes(orgdata,begin,count);
				 int result=ukeyAPI.SpcSymEncrypt(0x20, encrydata, count, szout, szoutblen, key);
				 if(result==0)
				 {
					 for(int m=0;m<szout.length;m++)
					 {
						 lastentrydata[i*count+m]=szout[m]; 
					 }
				 }
			 }
		 }
		 else
		 {
			 //非整数倍算法
			 k=orgdata.length/count+1; 
			 for(int i=0;i<k-1;i++)
			 {
				 begin=i*count;
				 byte[] encrydata=subBytes(orgdata,begin,count);
				 int result=ukeyAPI.SpcSymEncrypt(0x20, encrydata, count, szout, szoutblen, key);
				 if(result==0)
				 {
					 for(int m=0;m<szout.length;m++)
					 {
						 lastentrydata[i*count+m]=szout[m]; 
					 }
				 }
			 }
			 //最后一个不够八位数组的对象进行补位 0，再加密
			 byte[] lessb=subBytes(orgdata,(k-1)*count,orgdata.length%8);
			 byte[] lessbs=new byte[8];
			 for(int n=0;n<lessb.length;n++)
			 {
				 lessbs[n]=lessb[n] ;
			 }
			 int result=ukeyAPI.SpcSymEncrypt(0x20, lessbs, count, szout, szoutblen, key);
			 if(result==0)
			 {
				 for(int m=0;m<szout.length;m++)
				 {
					 lastentrydata[(k-1)*count+m]=szout[m]; 
				 } 
			 }
		 } 
		 orgdata=null;
		 return lastentrydata;
	}
	/*
	 * byte数组转化为十六进制
	 * */
	public  String bytesToHexString(byte[] b)
	{
		StringBuffer sb=new StringBuffer("");
		if(b==null||b.length==0){
			return "";
		}
		for(int i=0;i<b.length;i++)
		{
			int v=b[i] & 0xFF;
			String hv=Integer.toHexString(v);
			if(hv.length()<2)
			{
				sb.append(0);
			}
			sb.append(hv);
		}
		return sb.toString();
	}
	/*
	 * 截取byte数组中的字符按
	 * */
	public  byte[] subBytes(byte[] src,int begin,int count)
	{
		byte[] b=new byte[count];
		for(int i=begin;i<begin+count;i++)
		{
			b[i-begin]=src[i];
		}
		return b;
	}
}
