/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module;

import com.puzzle.module.send.excutor.MessageSendExcutor;
import com.puzzle.util.MessageExcutor;
import org.apache.commons.io.monitor.FileAlterationMonitor;

/**
 *
 * @author ljs
 */
public class MonitorDto {
    
    public FileAlterationMonitor sendMonitor;
    public FileAlterationMonitor receCiqMonitor;
    public FileAlterationMonitor receCusMonitor;
    public FileAlterationMonitor xmlBakMonitor;
    
    
    
    public static MonitorDto monitorDto=new MonitorDto() ;
  
    public static Object obj=new Object();
    
    private MonitorDto(){
        
    }

    
}
