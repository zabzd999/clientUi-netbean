/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puzzle.gui;
 
import java.io.File;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.RandomAccessFile;  
import java.nio.channels.FileChannel;  
import java.nio.channels.FileLock;  

import org.apache.log4j.Logger;
/**
 *
 * @author Administrator
 */
public class FileLockUtil {  
      
	private static Logger log = Logger.getLogger(FileLockUtil.class);  
    private static String fileName = "fileLock.lock";  
    private static String currentDirectory = System.getProperty("user.dir");  
    private static String fileSeparate = System.getProperty("file.separator");  
    private static File f = null;  
    private static RandomAccessFile raf = null;  
    private static FileChannel fc = null;  
    private static FileLock fl = null;  
    private static FileWriter fr = null;  
  
        
    public static boolean getFileLock()  
    {  
        boolean rtn = true;  
           
        try {  
            if ( !currentDirectory.endsWith(fileSeparate))  
            {  
                currentDirectory = currentDirectory+fileSeparate;  
            }  
//            System.out.println(currentDirectory);  
               f = new File(currentDirectory+fileName);  
               raf = new RandomAccessFile(f, "rw");  
  
               fr = new FileWriter(f);  
               fr.write("singleton");  
               fr.close();  
               fr = null;  
               fc = raf.getChannel();  
               fl = fc.tryLock();  
                 
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
        	log.error(e.getMessage(),e);
            e.printStackTrace();  
            return false;  
        }  
  
             
        return rtn;  
          
    }  
      
    public  static void releaseFileLock() throws IOException  
    {  
  
            try {  
                 if (fr != null)  
                     fr.close();  
                 if (fl != null)  
                     fl.release();  
                 if (fc != null)  
                     fc.close();  
                 if (raf != null)  
                     raf.close();  
                 if (f != null)  
                     f.delete();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
            	log.error(e.getMessage(),e);
                e.printStackTrace();  
                throw e;  
            }  
    }  
  
    public static void main(String[] args)  
    {  
//        FileLockUtil lockUtil = new FileLockUtil();  
        if ( FileLockUtil.getFileLock())  
        {  
            System.out.println("get file lock.");  
        }  
          
//        FileLockUtil lockUtil2 = new FileLockUtil();  
        if ( FileLockUtil.getFileLock())  
        {  
            System.out.println("get file lock2.");  
        }  
        else  
        {  
            System.out.println("can not get file lock2.");  
        }  
          
        try {  
            FileLockUtil.releaseFileLock();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block 
        	log.error(e.getMessage(),e);
            e.printStackTrace();  
        }  
          
        if ( FileLockUtil.getFileLock())  
        {  
            System.out.println("get file lock2.");  
        }  
        else  
        {  
            System.out.println("can not get file lock2.");  
        }  
          
    }  
} 
