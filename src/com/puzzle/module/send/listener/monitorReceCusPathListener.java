/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.listener;

import com.puzzle.gui.MainFrame;
import com.puzzle.util.DateUtil;
import com.puzzle.util.JtableMessageDto;
import com.puzzle.util.QueueUtils;
import java.io.File;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class monitorReceCusPathListener extends FileAlterationListenerAdaptor {
private static Logger log=Logger.getLogger(monitorReceCiqPathListener.class);

    @Override
    public void onFileCreate(File file) {
        super.onFileCreate(file);
        QueueUtils.receCusTable3.offer(new JtableMessageDto(file.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())));
              log.info("接收到海关回执 "+file.getName());
    }
}
