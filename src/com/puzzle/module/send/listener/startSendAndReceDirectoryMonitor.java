/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.listener;
import com.puzzle.module.FileFilterAndExcutor;
import com.puzzle.module.FileMonitorUtils;
import com.puzzle.module.receive.excutor.MessageReceCiqExcutor;
import com.puzzle.module.receive.excutor.MessageReceCusExcutor;
import com.puzzle.util.FileDto;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class startSendAndReceDirectoryMonitor  {

    private static Logger log=Logger.getLogger(startSendAndReceDirectoryMonitor.class);


    public  static void startMonitor() {

//        FileFilterAndExcutor filter = new FileFilterAndExcutor();
        long interval = 100L;
////        //监控发送报目录并更新相应的jtable1
//        monitorSendPathListener listener = new monitorSendPathListener(); //更新相应的table
//        FileMonitorUtils.monitorDircetory(FileDto.getSingleInstance().fileSendPath, interval, filter, listener);
        //更新cus接收报文的table3
        
        
       
            new SendPathFileScan().start();//启动扫描发送目录线程
        
        monitorReceCusPathListener listenercusrece = new monitorReceCusPathListener(); //更新相应的table
        FileMonitorUtils.monitorDircetory(FileDto.getSingleInstance().fileReceCusPath, interval, null, listenercusrece);
//
//        //更新ciq接收报文的table4
        monitorReceCiqPathListener ciql = new monitorReceCiqPathListener(); //更新相应的table
        FileMonitorUtils.monitorDircetory(FileDto.getSingleInstance().fileReceCiqPath, interval, null, ciql);
//
//        monitorSendSignPathListener sendError = new monitorSendSignPathListener(); //更新相应的table
//        FileMonitorUtils.monitorDircetory(FileDto.getSingleInstance().fileSignPath, interval, null, sendError);
        
//        启动ciq接收报文的线程
//        MessageReceCiqExcutor threadciq = new MessageReceCiqExcutor();
//        threadciq.start();
//////        //启动cus接收报文的线程
//        MessageReceCusExcutor rececusT = new MessageReceCusExcutor();
//        rececusT.start();
        log.info("已启动monitor");

    
}
    
    public static void main(String[] args) {
          String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + "kafka" + File.separator + "kafka_client_jaas.conf";
        System.setProperty("java.security.auth.login.config", path);

         FileDto.getSingleInstance().type="1";
        FileDto.getSingleInstance().reloadConfi("D:\\data\\send","1");
        startSendAndReceDirectoryMonitor.startMonitor();
    }
}