/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.listener;

import com.puzzle.module.send.excutor.MessageSendExcutor;
import com.puzzle.util.DateUtil;
import com.puzzle.util.FileDto;
import com.puzzle.util.MessageExcutor;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Administrator
 */
public class SendPathFileScan extends Thread {

    public void run() {
        File sc = new File(FileDto.getSingleInstance().fileSendPath);
        if (!sc.exists()) {
            sc.mkdirs();
        }
        MessageSendExcutor excutor = new MessageSendExcutor();
        while (true) {

            Collection<File> fs = FileUtils.listFiles(sc, new String[]{"xml"}, false);

            for (File f : fs) {
                excutor.excute(f);           

                if (FileDto.getSingleInstance().fileSendIsbak.equals("true")) {
                    try {
                        FileUtils.copyFileToDirectory(f, new File(FileDto.getSingleInstance().fileSendBakPath, DateUtil.getCurrDateMM()));
                    } catch (IOException ex) {
                       ex.printStackTrace();
                    }
                }
                

                f.delete();
            }
            
            
            
            
            
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
            

        }

    }
}
