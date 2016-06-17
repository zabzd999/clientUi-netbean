/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.util;

import com.hnblc.message.adaptors.MessageAdaptor;

/**
 *
 * @author ljs
 */
public class MessageAdaptorFactory {
    
    public static   MessageAdaptor adaptor = new MessageAdaptor();
    public static  MessageAdaptor getSingletonAdaptor(){
        return adaptor;
    }
    
   
}
