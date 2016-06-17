/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hnblc.sign.test;

import java.util.UUID;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class Test {
    public static void main(String[] args) {
        
             Executors.newFixedThreadPool(2).execute(new Runnable() {

            @Override
            public void run() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           
            
                     while(true){
                         System.out.println("099ijkl"+UUID.randomUUID().toString());
                         
                     }
            
            }
        });
        
        
    }
}
