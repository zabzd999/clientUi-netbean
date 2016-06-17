/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

import org.apache.commons.io.filefilter.FileFileFilter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;

/**
 *
 * @author ljs
 */
public class UkeyUtils {

    private static Logger log=Logger.getLogger(UkeyUtils.class);
    public static Properties Ukey=new Properties();
    static{
        
      InputStream in=  UkeyUtils.class.getResourceAsStream("Ukey.properties");
        try {
            Ukey.load(in);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UkeyUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static void main(String[] args) {
        System.out.println(UkeyUtils.Ukey.getProperty("Ukey.passwd"));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
