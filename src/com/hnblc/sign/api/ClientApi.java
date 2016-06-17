package com.hnblc.sign.api;

import java.io.UnsupportedEncodingException;

import com.hnblc.sign.entity.UkeyResult;
import com.hnblc.sign.util.UkeyJnaAPIHelper;
import com.puzzle.util.UkeyUtils;
import com.sun.jna.ptr.IntByReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

public class ClientApi {

    private static Logger log = Logger.getLogger(ClientApi.class);
    private static ClientApi api = new ClientApi();
    public UkeyJnaAPIHelper help = null;

    public static ClientApi getSingletonClientApi() {
        return api;
    }

    private ClientApi() {

    }



    public UkeyJnaAPIHelper getSingletonHelp() {
        if (help != null) {
            return help;
        }
        synchronized (ClientApi.class) {
            if (help == null) {
                UkeyResult urgetsignData = new UkeyResult();
                UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();
                UkeyResult uropen = new UkeyResult();
                uropen = help.SpcInitEnvEx();//222222
                if (1 == uropen.getFlag()) {
                    log.info(uropen.getDetail());
                    UkeyResult urgetverpwd = new UkeyResult();
                    String ueky = UkeyUtils.Ukey.getProperty("Ukey.passwd").trim();
                    urgetverpwd = help.SpcVerifyPIN(ueky, ueky.length());//2222222
                    if (1 == urgetverpwd.getFlag()) {
                        this.help = help;
                    } else {
                        log.info(urgetverpwd.getDetail());
                    }

                } else {
                    log.info(uropen.getDetail());
                }

            }

        }

        return this.help;
    }

//    public boolean validUkeyPasswd(String passwd) {
//        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();
//        UkeyResult uropen = new UkeyResult();
//        uropen = help.SpcInitEnvEx();
//        if (1 == uropen.getFlag()) {
//            UkeyResult urgetverpwd = new UkeyResult();
//            urgetverpwd = help.SpcVerifyPIN(passwd.trim(), passwd.length());
//
//            if (1 == urgetverpwd.getFlag()) {
//                log.info("Ukey口令验证成功");
//                return Boolean.TRUE;
//            } else {
//                log.info("Ukey口令验证失败");
//            }
//        } else {
//            log.info("打开Ukey设备失败");
//
//        }
//        return Boolean.FALSE;
//    }

    /*
     * 获取签名数据
     * */
    public byte[] getSingData(String Data, String UkeyPasswd) throws UnsupportedEncodingException {
        UkeyResult urgetsignData = new UkeyResult();

        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();
        UkeyResult uropen = new UkeyResult();
        uropen = help.SpcInitEnvEx();//222222
        if (1 == uropen.getFlag()) {
            log.info(uropen.getDetail());
            UkeyResult urgetverpwd = new UkeyResult();
//			urgetverpwd=help.SpcVerifyPIN("88888888", 8);
            urgetverpwd = help.SpcVerifyPIN(UkeyPasswd.trim(), UkeyPasswd.length());//2222222
            if (1 == urgetverpwd.getFlag()) {
                log.info(urgetverpwd.getDetail());

                byte[] outsignData = new byte[1024 * 5];
                IntByReference outsignDatalen = new IntByReference(outsignData.length);
                urgetsignData = help.SpcSignData(Data, Data.length(), outsignData, outsignDatalen);
                if (1 == urgetsignData.getFlag()) {
                    log.info(urgetsignData.getDetail());
                } else {
                    log.info(urgetsignData.getDetail());
                }
            } else {
                log.info(urgetverpwd.getDetail());
            }

        } else {
            log.info(uropen.getDetail());
        }
        return urgetsignData.getByteResult();
    }

    public byte[] getSignData2(String data) {

        UkeyJnaAPIHelper help = getSingletonHelp();
         if(help==null) return null;
        UkeyResult urgetsignData = new UkeyResult();
        byte[] outsignData = new byte[1024 * 5];
        IntByReference outsignDatalen = new IntByReference(outsignData.length);
        urgetsignData = help.SpcSignData(data, data.length(), outsignData, outsignDatalen);
        if (1 == urgetsignData.getFlag()) {
            log.info(urgetsignData.getDetail());
            return urgetsignData.getByteResult();
        } else {
            this.help=null;
            log.info(urgetsignData.getDetail());
        }

        return null;

    }

    /*
     * 获取设备编号
     * */
    public String getCardID() throws UnsupportedEncodingException {
        UkeyResult urCardID = new UkeyResult();
        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();
        UkeyResult uropen = new UkeyResult();
        uropen = help.SpcInitEnvEx();
        if (1 == uropen.getFlag()) {
            log.info(uropen.getDetail());
            byte[] cardid = new byte[20];
            IntByReference cardidlen = new IntByReference(cardid.length);
            urCardID = help.SpcGetCardID(cardid, cardidlen);
            if (1 == urCardID.getFlag()) {
                log.info(urCardID.getDetail());
            } else {
                log.info(urCardID.getDetail());
            }
        } else {
            log.info(uropen.getDetail());
        }
        return new String(urCardID.getByteResult(), "UTF-8").trim();
    }

    public String getCardID2() throws UnsupportedEncodingException {
        UkeyJnaAPIHelper help = getSingletonHelp();
        if(help==null) return null;
        UkeyResult urCardID = new UkeyResult();
        byte[] cardid = new byte[20];
        IntByReference cardidlen = new IntByReference(cardid.length);
        urCardID = help.SpcGetCardID(cardid, cardidlen);
        if (1 == urCardID.getFlag()) {
            log.info(urCardID.getDetail());
            return new String(urCardID.getByteResult(), "UTF-8").trim();
        } else {
            this.help=null;
            log.info(urCardID.getDetail());
        }

        return null;

    }

    /*
     * 3DES 加密  keys 必须二十四位
     * */
    public byte[] getEncryptDate(String orgData, String keys) {
        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();
        return help.SpcSymEncrypt3DES(orgData, keys);
    }

    /*
     * 3DES 解密  keys 必须二十四位
     * */
    public byte[] getDecryptDate(byte[] encryDate, String keys) {
        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();
        return help.SpcSymDecrypt3DES(encryDate, keys);
    }

    /*
     * 
     *获取签名证书der
     * */
    public byte[] getSignCert() {
        UkeyResult urgetsignCert = new UkeyResult();

        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();

        UkeyResult uropen = new UkeyResult();
        uropen = help.SpcInitEnvEx();
        if (1 == uropen.getFlag()) {
            log.info(uropen.getDetail());
            byte[] b = new byte[1024 * 2];
            IntByReference blen = new IntByReference(b.length);
            urgetsignCert = help.SpcGetSignCert(b, blen);
            if (1 == urgetsignCert.getFlag()) {
                log.info(urgetsignCert.getDetail());
            } else {
                log.info(urgetsignCert.getDetail());
            }
        } else {
            log.info(uropen.getDetail());
        }
        return urgetsignCert.getByteResult();
    }

    /*
     * 
     *获取加密证书der
     * */
    public byte[] getEncryCert() {
        UkeyResult urEncryCer = new UkeyResult();

        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();

        UkeyResult uropen = new UkeyResult();
        uropen = help.SpcInitEnvEx();
        if (1 == uropen.getFlag()) {
            log.info(uropen.getDetail());
            byte[] b = new byte[1024 * 2];
            IntByReference blen = new IntByReference(b.length);
            urEncryCer = help.SpcGetEnvCert(b, blen);
            if (1 == urEncryCer.getFlag()) {
                log.info(urEncryCer.getDetail());
            } else {
                log.info(urEncryCer.getDetail());
            }
        } else {
            log.info(uropen.getDetail());
        }
        return urEncryCer.getByteResult();
    }

    /*
     * 取随机数
     * */
    public byte[] getRandom() {
        UkeyResult urgetRandom = new UkeyResult();

        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();

        UkeyResult uropen = new UkeyResult();
        uropen = help.SpcInitEnvEx();
        if (1 == uropen.getFlag()) {
            log.info(uropen.getDetail());
            byte[] b = new byte[100];
            urgetRandom = help.SpcGetRandom(b, 8);
            if (1 == urgetRandom.getFlag()) {
                log.info(urgetRandom.getDetail());
            } else {
                log.info(urgetRandom.getDetail());
            }
        } else {
            log.info(uropen.getDetail());
        }
        return urgetRandom.getByteResult();

    }

    public byte[] getRSAEncrypt(String data) throws UnsupportedEncodingException {
        byte[] dercert = getEncryCert();
        UkeyResult urRSAEncrypt = new UkeyResult();
        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();

        UkeyResult urgetverpwd = new UkeyResult();
//		 urgetverpwd=help.SpcVerifyPIN("11111111", 8);
        urgetverpwd = help.SpcVerifyPIN("88888888", 8);
        if (1 == urgetverpwd.getFlag()) {
            log.info(urgetverpwd.getDetail());
            byte[] openData = new byte[1024 * 5];
            urRSAEncrypt = help.SpcRSAEncrypt(dercert, dercert.length, data, data.length(), openData, new IntByReference(openData.length));
            if (1 == urRSAEncrypt.getFlag()) {
                log.info(urRSAEncrypt.getDetail());
            } else {
                log.info(urRSAEncrypt.getDetail());
            }
        } else {
            log.info(urgetverpwd.getDetail());
        }

        return urRSAEncrypt.getByteResult();
    }

    public byte[] getRSADecrypt(byte[] data) throws UnsupportedEncodingException {
        UkeyResult urRSADecrypt = new UkeyResult();
        UkeyJnaAPIHelper help = new UkeyJnaAPIHelper();

        UkeyResult uropen = new UkeyResult();
        uropen = help.SpcInitEnvEx();
        if (1 == uropen.getFlag()) {
            log.info(uropen.getDetail());
            UkeyResult urgetverpwd = new UkeyResult();
//		 urgetverpwd=help.SpcVerifyPIN("11111111", 8);
            urgetverpwd = help.SpcVerifyPIN("88888888", 8);
            if (1 == urgetverpwd.getFlag()) {
                log.info(urgetverpwd.getDetail());
                byte[] openData = new byte[1024 * 5];
                urRSADecrypt = help.SpcRSADecrypt(data, data.length, openData, new IntByReference(openData.length));
                if (1 == urRSADecrypt.getFlag()) {
                    log.info(urRSADecrypt.getDetail());
                } else {
                    log.info(urRSADecrypt.getDetail());
                }
            } else {
                log.info(urgetverpwd.getDetail());
            }
        } else {
            log.info(uropen.getDetail());
        }

        return urRSADecrypt.getByteResult();
    }
}
