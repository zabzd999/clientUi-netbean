/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puzzle.gui.tip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JDialog;
/**
 *
 * @author Administrator
 */
public class TipWindow extends JDialog{   

private static final long serialVersionUID = 8541659783234673950L;
private static Dimension dim;   
    private int x, y;   
    private int width, height;   
    private static Insets screenInsets;     

   
    public TipWindow(int width,int height){ 
    this.width=width;
    this.height=height;
        dim = Toolkit.getDefaultToolkit().getScreenSize();   
        screenInsets   =   Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration()); 
        x = (int) (dim.getWidth() - width-3);   
        y = (int) (dim.getHeight()-screenInsets.bottom-3); 
    initComponents();
    } 
    public void run() {   
        for (int i = 0; i <= height; i += 10) {   
           try {   
                this.setLocation(x, y - i); 
                Thread.sleep(8);   
            } catch (InterruptedException ex) {      
            }   
        }
//        此处代码用来实现让消息提示框5秒后自动消失,如果不需要自动关闭,则可注释掉
        try {
    Thread.sleep(50000);
   } catch (InterruptedException e) {
    e.printStackTrace();
   }
        close(); // 窗口关闭
    }     
    private void initComponents() {   
        this.setSize(width, height);   
        this.setLocation(x, y);   
        this.setBackground(Color.black);   
     
    }    
    public void close(){
    x=this.getX();
    y=this.getY();
    int ybottom=(int)dim.getHeight()-screenInsets.bottom;
    for (int i = 0; i <= ybottom-y; i += 10) {   
            try {             
                setLocation(x, y+i); 
                Thread.sleep(5);                                   
            } catch (InterruptedException ex) {    
            }   
        } 
    dispose();
    } 
}
