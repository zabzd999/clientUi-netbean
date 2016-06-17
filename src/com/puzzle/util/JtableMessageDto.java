/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

/**
 *
 * @author ljs
 */
public class JtableMessageDto {
    
    private String fileName;
    private String fileTime;

    
    public JtableMessageDto(String fileName ,String fileTime ){
        this.fileName=fileName;
        this.fileTime=fileTime;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileTime
     */
    public String getFileTime() {
        return fileTime;
    }

    /**
     * @param fileTime the fileTime to set
     */
    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }
    
}
