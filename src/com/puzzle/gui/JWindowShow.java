/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.gui;

import javax.swing.*;

import java.awt.*;
import java.net.*;

public class JWindowShow extends JFrame implements Runnable {

    Thread splashThread; // 进度条更新线程  
    JProgressBar progress; // 进度条  

    public static Boolean flag1 = Boolean.FALSE;

    public JWindowShow() {
        this.setUndecorated(true);
        Container container = getContentPane(); // 得到容器  
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // 设置光标  
        URL url = getClass().getResource("splash.jpg"); // 图片的位置  
        if (url != null) {
            container.add(new JLabel(new ImageIcon(url)), BorderLayout.CENTER); // 增加图片  
        }
        progress = new JProgressBar(1, 100); // 实例化进度条  
        progress.setStringPainted(true); // 描绘文字  
        progress.setString("程序正在加载,请稍候......"); // 设置显示文字  
        progress.setBackground(Color.white); // 设置背景色
        progress.setForeground(new Color(8, 46, 84));
        container.add(progress, BorderLayout.SOUTH); // 增加进度条到容器上  

        Dimension screen = getToolkit().getScreenSize(); // 得到屏幕尺寸  
        pack(); // 窗口适应组件尺寸  
        setLocation((screen.width - getSize().width) / 2,
                (screen.height - getSize().height) / 2); // 设置窗口位置  
    }

    public void start() {
        this.toFront(); // 窗口前端显示  
        splashThread = new Thread(this); // 实例化线程  
        splashThread.start(); // 开始运行线程  
    }

    public void run() {
        setVisible(true); // 显示窗口  
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(30); // 线程休眠  
                progress.setValue(progress.getValue() + 4); // 设置进度条值  
                if (JWindowShow.flag1) {
                    progress.setValue(100); // 设置进度条值  
                    break;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dispose(); // 释放窗口  

    }

    public static class MainThread extends Thread {

        public void run() {
            try {
                MainFrame.main(null);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        JWindowShow splash = new JWindowShow();
        splash.start(); // 运行启动界面  
        new MainThread().start();// 运行主程序  
    }
}
