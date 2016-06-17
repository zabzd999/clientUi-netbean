/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.checkXml;

import com.puzzle.util.FileDto;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class xmlCheck {

    private static Logger log =Logger.getLogger(xmlCheck.class);
    /**
     *
     * @param xml
     * @return
     */
    public static   XmlCheckResult isValid(String content){ 
          XmlCheckResult checkresult=null;
          
           if(FileDto.getSingleInstance().type==null){
               log.error("获取文件类型失败,程序即将停止");
              try {
                  Thread.sleep(3000L);
              } catch (InterruptedException ex) {
                 ex.printStackTrace();
              }
               System.exit(0);
           }//获取文件类型
              if(FileDto.getSingleInstance().type.equals("1")){
                  checkresult=isOrderValid(content);
              }
              else if(FileDto.getSingleInstance().type.equals("2")){
                  checkresult=isWaybillValid(content);
              }
             else if(FileDto.getSingleInstance().type.equals("3")){
                  checkresult=isPaymentValid(content);
              }
             else if(FileDto.getSingleInstance().type.equals("4")){
                  checkresult=isQingdanValid(content);
              }
        return checkresult;
    }
    
    public static XmlCheckResult isOrderValid(String xml){
        XmlCheckResult r=new XmlCheckResult();
        
        
        
        r.isValid=true;
        return r;
    }
    public static XmlCheckResult isWaybillValid(String xml){
              XmlCheckResult r=new XmlCheckResult();
        
        
        
        r.isValid=true;
        return r;
    }
    public static XmlCheckResult isQingdanValid(String xml){
              XmlCheckResult r=new XmlCheckResult();
        
        
        
        r.isValid=true;
        return r;
    }
    public static XmlCheckResult isPaymentValid(String xml){
              XmlCheckResult r=new XmlCheckResult();
        
        
        
        r.isValid=true;
        return r;
    }
    
}
