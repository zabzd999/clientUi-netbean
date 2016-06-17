/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module;

import java.util.logging.Level;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;

/**
 *
 * @author ljs
 */
public class FileMonitorUtils {

    private FileAlterationObserver observer;
    private FileAlterationMonitor monitor;
    private static Logger log = Logger.getLogger(FileMonitorUtils.class);

    public static boolean monitorDircetory(String scanPath, long interval, FileFileFilter f, FileAlterationListenerAdaptor listener) {

        if (scanPath == null) {
            return false;
        }
        log.info("监控" + scanPath);
        FileAlterationObserver observer = new FileAlterationObserver(scanPath, f);

        observer.addListener(listener);

        FileAlterationMonitor fileMonitor = new FileAlterationMonitor(
                interval, new FileAlterationObserver[]{observer});
        try {
            fileMonitor.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

//
}
