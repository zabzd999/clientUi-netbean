/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.sign;

import com.hnblc.sign.api.ClientApi;
import com.hnblc.sign.entity.UkeyResult;
import com.hnblc.sign.util.UkeyJnaAPIHelper;
import com.puzzle.util.ConfigUtils;
import com.puzzle.util.UkeyUtils;
import com.sun.jna.ptr.IntByReference;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ljs
 */
public class SignXml {

    private static Logger log = Logger.getLogger(SignXml.class);

    /**
     * 给定拆分后的xml生成签名后的xml
     *
     * @param xml
     * @return
     */
    public static String sign(String xml) {

        xml = xml.substring(xml.indexOf("encoding=\"UTF-8\"?>") + "encoding=\"UTF-8\"?>".length() + 1, xml.length());//去头
        ClientApi api = ClientApi.getSingletonClientApi();
        StringBuffer b = new StringBuffer();
        StringBuffer tempXml =null;
        try {

            //传输企业配置：
            String copCode = ConfigUtils.getSingleInstance().config.getProperty("copCode");
            String dxpMode = ConfigUtils.getSingleInstance().config.getProperty("dxpMode");
            String copName = ConfigUtils.getSingleInstance().config.getProperty("copName");
            String dxpId = ConfigUtils.getSingleInstance().config.getProperty("dxpId");
            String cebHeadMessage = ConfigUtils.getSingleInstance().config.getProperty("cebHeadMessage").trim();
//            if (cebHeadMessage.equals("CEB311Message")||cebHeadMessage.equals("CEB621Message")||cebHeadMessage.equals("CEB511Message")) {
//                String appTime = StringUtils.substringBetween(xml, "<appTime>", "</appTime>");
//                appTime = appTime.substring(0, 8);
//               
//                int st1 = xml.indexOf("<appTime>");
//                int st2 = xml.indexOf("</appTime>");
//                xml = xml.substring(0, st1 + "<appTime>".length()) + appTime + xml.substring(st2, xml.length());
//
//            }
            //签名和sha1配置

            //封装成最终海关需要的xml文件
            b.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")//夹头
                    .append("<" + cebHeadMessage + "  xmlns=\"http://www.chinaport.gov.cn/ceb\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" guid=\"" + UUID.randomUUID().toString() + "\" version=\"v1.0\">")
                    .append(xml).append("<BaseTransfer>\n"
                            + "		<copCode>").append(copCode).append("</copCode>\n"
                            + "		<copName>").append(copName).append("</copName>\n"
                            + "		<dxpMode>").append(dxpMode).append("</dxpMode>\n"
                            + "		<dxpId>").append(dxpId).append("</dxpId>\n"
                            + "		<note></note>\n"
                            + "	</BaseTransfer>\n");
            tempXml= new StringBuffer(b);
            String spliteCusXmlbeforeSign = b.append("</" + cebHeadMessage + ">").toString();

            byte[] signData = api.getSignData2(spliteCusXmlbeforeSign);
            if (signData == null) {
                log.info("调用签名API失败");
                return null;
            }
            String SignatureValue = Base64.encodeBase64String(signData);
//            String SignatureValue = new String(signData);

            String DigestValue = getSha_1zhaiyao(spliteCusXmlbeforeSign);

            String KeyName = getKeyName();
            if (KeyName == null) {
                log.info("读取UKEY设备号失败");
                return null;
            }
//            StringBuffer b2 = new StringBuffer();
            tempXml.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n"
                    + "<ds:SignedInfo>\n"
                    + "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod>\n"
                    + "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>\n"
                    + "<ds:Reference URI=\"\">\n"
                    + "<ds:Transforms>\n"
                    + "<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>\n"
                    + "</ds:Transforms>\n"
                    + "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod><ds:DigestValue>").append(DigestValue).
                    append("</ds:DigestValue></ds:Reference>\n"
                            + "</ds:SignedInfo>\n"
                            + "<ds:SignatureValue>").append(SignatureValue).append("</ds:SignatureValue>\n"
                            + "<ds:KeyInfo>\n"
                            + "<ds:KeyName>").append(KeyName).append("</ds:KeyName>\n"
                            + "</ds:KeyInfo>\n"
                            + "</ds:Signature>\n</" + cebHeadMessage + ">");

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
        return tempXml.toString();
    }

//    public void 
    /**
     *
     * @param xml
     * @return
     */
    public static String getSha_1zhaiyao(String xml) {
        return Base64.encodeBase64String(DigestUtils.sha1(xml));
    }

    public static String getKeyName() {

        try {
            String idNumber = ClientApi.getSingletonClientApi().getCardID2();
            return idNumber;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String sign2(String xml) {
        String digestValue = getSha_1zhaiyao(xml);

        byte[] signData = ClientApi.getSingletonClientApi().getSignData2(xml);
        if (signData == null) {
            log.info("调用签名API失败");
            return null;
        }

        String signNature = Base64.encodeBase64String(signData);
        String keyName = getKeyName();
        if (keyName == null) {
            log.info("读取UKEY设备号失败");
            return null;
        }

        String tail = xml.substring(xml.lastIndexOf("</"), xml.lastIndexOf(">") + 1);
        xml = xml.substring(0, xml.lastIndexOf("</"));

        StringBuffer buffer = new StringBuffer();
        buffer.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n"
                + "<ds:SignedInfo>\n"
                + "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod>\n"
                + "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>\n"
                + "<ds:Reference URI=\"\">\n"
                + "<ds:Transforms>\n"
                + "<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>\n"
                + "</ds:Transforms>\n"
                + "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>\n"
                + "<ds:DigestValue>");
        buffer.append(digestValue);
        buffer.append("</ds:DigestValue>\n"
                + "</ds:Reference>\n"
                + "</ds:SignedInfo>\n"
                + "<ds:SignatureValue>");
        buffer.append(signNature);
        buffer.append("</ds:SignatureValue>\n"
                + "<ds:KeyInfo>\n"
                + "<ds:KeyName>");
        buffer.append(keyName);
        buffer.append("</ds:KeyName>\n"
                + "</ds:KeyInfo>\n"
                + "</ds:Signature>").append(tail);

        xml = xml + buffer.toString();
        return xml;
    }

    public static void main(String[] args) throws Exception {
        ClientApi api = ClientApi.getSingletonClientApi();
        String xml = FileUtils.readFileToString(new File("D:\\work\\myeclipse2014\\workspace\\Ukey\\sign.xml"), "UTF-8");
//            
        ExecutorService s = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 1; i++) {
            s.execute(new signTest(xml));
        }

//        String signData = Base64.encodeBase64String(signedData);
//        FileUtils.writeStringToFile(new File("D:\\sign.txt"), signData, "UTF-8");
//        System.out.println(signData);
    }

    public static class signTest extends Thread {

        public signTest(String ss) {
            signData = ss;
        }
        public String signData;

        public void run() {

            long start = System.currentTimeMillis();
            byte[] signedData = ClientApi.getSingletonClientApi().getSignData2(signData);
            long stop = System.currentTimeMillis();
            System.out.println("线程ID" + this.getId() + (stop - start));
        }

    }

}
