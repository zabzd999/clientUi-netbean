/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module;

import com.puzzle.module.send.excutor.MessageSendExcutor;
import com.puzzle.util.MessageExcutor;
import java.io.File;
import java.util.logging.Level;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
/**
 *
 * @author Administrator
 */
public class FileFilterAndExcutor extends  FileFileFilter {
    
    private MessageSendExcutor sendExe;
    public FileFilterAndExcutor( /**MessageSendExcutor sendExe**/){
//      this.sendExe=sendExe;
    }
    private static Logger log=Logger.getLogger(FileFilterAndExcutor.class);

            public boolean accept(File f) {
            if (f.getName().matches("^.*.xml$") && f.renameTo(f)) {   
//               this. sendExe.excute(f);
                return true;
            }

            return false;
        }
}
