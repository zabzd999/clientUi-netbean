/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.excutor;

import com.puzzle.module.send.checkXml.XmlCheckResult;
import com.puzzle.module.send.checkXml.xmlCheck;
import com.puzzle.module.send.kafka10.KafkaProducerClient;
import com.puzzle.module.send.sign.SignXml;
import com.puzzle.module.send.spliteXml.SpliteXml;
import com.puzzle.util.DateUtil;
import com.puzzle.util.FileDto;
import com.puzzle.util.JtableMessageDto;
import com.puzzle.util.QueueUtils;
import java.io.File;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * 1.调用报文校验方法。报文校验异常扔到校验异常文件夹下 2.调用拆分方法拆分成海关和国检的报文格式 3.分别向海关和国检发送报文
 * 4.发送成功后删除文件(同时触发文件删除的监听)
 *
 * 分别向海关和国检的MQ队列发送报文，
 *
 * @author Administrator
 */
public class MessageSendExcutor {

    private static Logger log = Logger.getLogger(MessageSendExcutor.class);

    public void excute(File xml) {
        KafkaProducerClient client = null;

        try {
            String content = FileUtils.readFileToString(xml, "UTF-8");

            XmlCheckResult checkResult = xmlCheck.isValid(content);
            if (!checkResult.isValid) {//报文校验

                FileUtils.copyFileToDirectory(xml, new File(FileDto.getSingleInstance().errorPath), true);

                log.error(xml.getAbsolutePath() + "文件校验失败");
                return;
            }

            boolean tr = true;
            if (content.indexOf("<CEB625Message") != -1 || content.indexOf("<CEB623Message") != -1 || content.indexOf("<CEB711Message") != -1||content.indexOf("<CEB513Message") != -1) {//判断是退换货的报文

                String signedXml = SignXml.sign2(content);
                if (signedXml == null || signedXml.equals("")) {
                    return;
                }
                
                    File signFile = new File(FileDto.getSingleInstance().fileSignPath, "签名后-" + DateUtil.getCurrDateMM2() + xml.getName());
                    FileUtils.writeStringToFile(signFile, signedXml, "UTF-8");
                if (FileDto.getSingleInstance().sendCusisSelected) {
                    client = KafkaProducerClient.getSingletonInstance();
                    client.send(FileDto.getSingleInstance().cusSendTopic, signedXml);
                    QueueUtils.sendSignTable.offer(new JtableMessageDto(signFile.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())));

                    log.info(xml.getName() + "海关报文发送成功");
                }


            } else {

                String splitXml = SpliteXml.splite(content);

                if (splitXml == null) {
                    return;
                }
                log.info(xml.getName() + "文件已拆分");

                splitXml = SignXml.sign(splitXml);// 封装签名后的报文

                if (splitXml == null) {
                    log.info("处理异常>>>>签名失败");
                    return;
                }

                File signFile = new File(FileDto.getSingleInstance().fileSignPath, "签名后-" + DateUtil.getCurrDateMM2() + xml.getName());
                FileUtils.writeStringToFile(signFile, splitXml, "UTF-8");
     
//          

                if (FileDto.getSingleInstance().sendCusisSelected) {
                    client = KafkaProducerClient.getSingletonInstance();
                    client.send(FileDto.getSingleInstance().cusSendTopic, splitXml);
                    QueueUtils.sendSignTable.offer(new JtableMessageDto(signFile.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())));

                     log.info(xml.getName() + "海关报文发送成功");
                }

                if (FileDto.getSingleInstance().sendCiqisSelected) {
                    client = KafkaProducerClient.getSingletonInstance();
                    client.send(FileDto.getSingleInstance().sendTpoic, content);//发送综合报文 
                    QueueUtils.sendTable1.offer(new JtableMessageDto(xml.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())));
     
                    log.info(xml.getName() + "大报文发送成功");
                }

            }

            
        } catch (Exception e) {
            e.printStackTrace();
            if (client != null) {
                client.close();
            }
        }

    }

}
