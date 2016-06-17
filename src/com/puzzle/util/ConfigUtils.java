/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

import com.puzzle.database.dao.UserDao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.StringReader;

/**
 *
 * @author ljs
 */
public class ConfigUtils {

    public Properties config = null;
    public Properties configActivemq = null;
//    public String fileType = null;
    public static Logger log = Logger.getLogger(ConfigUtils.class);

    public String config_properties =null;
    public String activemq_properties =null;
    public static ConfigUtils configutils = null;

    public static ConfigUtils getSingleInstance() {
        if (configutils != null) {
            return configutils;
        }
        synchronized (ConfigUtils.class) {
            if (configutils == null) {
                configutils = new ConfigUtils();
            }

        }

        return configutils;
    }

    private ConfigUtils() {
        config = new Properties();
        configActivemq = new Properties();
       StringReader in = null;
        StringReader inA = null;
        try {
            
              config_properties = UserDao.dao.queryForConfigPerperties(FileDto.getSingleInstance().type).getXmlValue();
              activemq_properties = UserDao.dao.queryForActiveMq().getXmlValue();
            in = new StringReader(config_properties);
            config.load(in);

            inA = new StringReader(activemq_properties);
            configActivemq.load(inA);
            
            in.close();
            inA.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        } 

    }


}
