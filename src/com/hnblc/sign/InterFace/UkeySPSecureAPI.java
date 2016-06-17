package com.hnblc.sign.InterFace;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface UkeySPSecureAPI extends StdCallLibrary{
	/*
	 * 直接使用dll文件名，不需要后缀，可以跨平台操作so文件
	 * linux文件下的 so 文件 必须是lib开头的
	 * windows系统自动加载顺序为  当前类文件的目录下查找->工程文件夹下win32/win64文件夹->系统WINDOWS system32下查找
	 * 
	 * */
//	UkeySPSecureAPI instance=(UkeySPSecureAPI)Native.loadLibrary(CommonProperties.getString("dll"), UkeySPSecureAPI.class);
	UkeySPSecureAPI instance=(UkeySPSecureAPI)Native.loadLibrary("SPSecureAPI", UkeySPSecureAPI.class);
	int SpcGetSignCert(byte[] szcert,IntByReference szcertlen);
	int SpcInitEnvEx();
	int SpcClearEnv();
	 String SpcGetErrMsg(int errorcode);
//	 byte[] SpcGetErrMsg(int errorcode);
	int SpcGetRandom(byte[] random,int randomlen);
	int SpcGetCertInfo(byte[] szcert,int szcertlen,int index,byte[] szout,IntByReference szoutlen);
	 int SpcVerifyPIN(String password,int pwdlen); 
//	 int SpcVerifyPIN(byte[] password,int pwdlen); 
	 int SpcChangePIN(String orgpassword,int orgpwdlen,String newpassword,int newpwdlen);
	 int SpcSymEncrypt(int  flag,byte[] orgData,int orgDatalen,byte[] szout,IntByReference szoutlen,byte[] key);
	 int SpcSymDecrypt(int  flag,byte[] cipherData,int cipherDatalen,byte[] outopenData,IntByReference outopenDatalen,byte[] key);
	  int SpcGetEnvCert(byte[]  encryptcertcontent,IntByReference encryptcertcontentlen);
	  int SpcRSAEncrypt(byte[] certcontent,int certcontentlen,String cipherData,int cipherDatalen,byte[] outopenData,IntByReference outopenDatalen);
	  int SpcRSADecrypt(byte[] ciphercontent,int ciphercontentlent,byte[]  openData,IntByReference outopenDatalen);
	  int SpcSignData(String signData,int signDatalen,byte[] outsignData,IntByReference outsignDatalen);
	  int SpcGetCertNo(byte[] cerno,IntByReference cernolen);
	  int SpcVerifySignData(byte[] certcontent,int certcontentlen,String orgDatacontent,int orgDatacontentlen,String signData,int signDatalen);
	  int SpcGetCardID(byte[] cardid,IntByReference cardidlen);
}
