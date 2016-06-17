/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

import com.puzzle.module.send.listener.startSendAndReceDirectoryMonitor;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ljs
 */
public class FileDto {

    private static Logger log = Logger.getLogger(FileDto.class);
    public static FileDto fileDto = null;
    public String fileName = "";

    public boolean sendCusisSelected = true;
    public boolean sendCiqisSelected = true;
    public boolean receCusisSelected = true;
    public boolean receCiqisSelected = true;

    public String errorPath;
    public String keptNday = "5";//备份文件最多保存几天
    public  String type;
    public String fileRootPath;
    public String fileSendPath;
    public String fileSignPath;
    public String fileReceCusPath=null;
    public String fileReceCiqPath=null;

    public String fileSendIsbak;
    public String fileSendBakPath;

    public String sendTpoic;
    public String cusSendTopic;
    public String receQueue;
    public String receCusQueue;

    private FileDto() {
//        fileRootPath = ConfigUtils.config.getProperty("file.root.path");
//        fileSendPath = fileRootPath + File.separator + "send";



    }

    public static FileDto getSingleInstance() {
        if (fileDto != null) {
            return fileDto;
        }
        synchronized (FileDto.class) {

            fileDto = new FileDto();
        }

        return fileDto;
    }

  

    /**
     *
     * @param rootPath 用户传入的大报文所在目录
     */
    public  void reloadConfi(String userXmlPath, String fileType) {

//          type = fileType;
        log.info("reloadconfig");
         ConfigUtils cc=   ConfigUtils.getSingleInstance();

                fileSendIsbak =cc.config.getProperty("file.send.isbak");
//        fileSendBakPath = fileRootPath + File.separator + "send" + File.separator + "bak";
        sendTpoic = ConfigUtils.getSingleInstance().config.getProperty("send.tpoic");
        cusSendTopic = ConfigUtils.getSingleInstance().config.getProperty("cus.send.topic");
        receQueue = ConfigUtils.getSingleInstance().config.getProperty("rece.queue");
        receCusQueue = ConfigUtils.getSingleInstance().config.getProperty("rece.cus.queue");



        if (StringUtils.isBlank(sendTpoic)) {
            System.exit(0);
        }
        if (StringUtils.isBlank(cusSendTopic)) {
            System.exit(0);
        }

//              String[]  names =rootPath.split("\\\\");
        String rootPath = userXmlPath.substring(0, userXmlPath.lastIndexOf("\\"));
//              String ff=names[0]+names[names.length-2];//
        fileRootPath = rootPath;// ConfigUtils.config.getProperty("file.root.path");
        String sendDirectoryName = userXmlPath.substring(userXmlPath.lastIndexOf("\\") + 1, userXmlPath.length());
        fileSendPath = userXmlPath;
        fileSignPath = userXmlPath + File.separator + "sign";
        fileSendBakPath = userXmlPath + File.separator + "bak";
        errorPath = userXmlPath + File.separator + "error";
       if(StringUtils.isBlank(fileReceCusPath))  fileReceCusPath = rootPath + File.separator + "receive" + File.separator + "cus";
       
        if(StringUtils.isBlank(fileReceCiqPath)) fileReceCiqPath = rootPath + File.separator + "receive" + File.separator + "ciq";
      
        File fileSendBakPath1 = new File(fileSendBakPath);
        if (!fileSendBakPath1.exists()) {
            fileSendBakPath1.mkdirs();
        }
        File signPath = new File(fileSignPath);
        if (!signPath.exists()) {
            signPath.mkdirs();
        }

        File fileSend = new File(fileSendPath);
        if (!fileSend.exists()) {
            fileSend.mkdirs();
        }
        File fileReceCus = new File(fileReceCusPath);
        if (!fileReceCus.exists()) {
            fileReceCus.mkdirs();
        }
        File fileReceCiq = new File(fileReceCiqPath);
        if (!fileReceCiq.exists()) {
            fileReceCiq.mkdirs();
        }

    }

}
