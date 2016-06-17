/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.listener;

import com.puzzle.module.send.excutor.MessageSendExcutor;
import com.puzzle.util.DateUtil;
import com.puzzle.util.ErrorFile;
import com.puzzle.util.JtableMessageDto;
import com.puzzle.util.QueueUtils;
import java.io.File;
import java.util.Date;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

/**
 *
 * @author Administrator
 */
public class monitorSendPathListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileDelete(File file) {
        System.out.println("file: " + file.getAbsolutePath() + " is deleted");
            QueueUtils.sendTable1.offer(new JtableMessageDto(file.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())));
     
        super.onFileDelete(file);
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("file: " + file.getAbsolutePath() + " is created.");

        MessageSendExcutor ex = new MessageSendExcutor();//单线程
        ex.excute(file);
        super.onFileCreate(file);
    }

}
