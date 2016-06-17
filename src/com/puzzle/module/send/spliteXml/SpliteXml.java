/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.module.send.spliteXml;

//import com.hnblc.message.adaptors.MessageAdaptor;
import com.puzzle.util.FileDto;
import com.puzzle.util.MessageAdaptorFactory;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SpliteXml {
    private static Logger log=Logger.getLogger(SpliteXml.class);
    public static String splite(String content){
        String result=null; 

        
          String[] contencus = MessageAdaptorFactory.getSingletonAdaptor().convertDeclareMessage(content, "cus", "OR");
          System.out.println(contencus[0]);
          return contencus[0];   
    }
 
    
    
    public static void main(String[] args) throws IOException {
        FileDto.getSingleInstance().type="1";
        splite( FileUtils.readFileToString(new File("D:\\java\\netbeans8\\work\\JavaApplication3\\doc\\erpQQ.xml"), "UTF-8"));
        
        
    }
    
    
}
