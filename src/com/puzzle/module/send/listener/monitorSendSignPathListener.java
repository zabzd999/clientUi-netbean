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

/**
 *
 * @author Administrator
 */
 public class monitorSendSignPathListener extends FileAlterationListenerAdaptor {


        @Override
        public void onFileCreate(File file) {
            super.onFileCreate(file);
              QueueUtils.sendSignTable.offer(new JtableMessageDto(file.getAbsolutePath(),DateUtil.getFormatDateTime(new Date())));

        }
    }
