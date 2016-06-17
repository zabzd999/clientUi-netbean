/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.database;

/**
 *
 * @author Administrator
 */
public class Entity {

    private String xmlName;
    private String xmlValue;
    private String fileType;

    /**
     * @return the xmlName
     */
    public String getXmlName() {
        return xmlName;
    }

    /**
     * @param xmlName the xmlName to set
     */
    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    /**
     * @return the xmlValue
     */
    public String getXmlValue() {
        return xmlValue;
    }

    /**
     * @param xmlValue the xmlValue to set
     */
    public void setXmlValue(String xmlValue) {
        this.xmlValue = xmlValue;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
