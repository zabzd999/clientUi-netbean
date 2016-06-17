/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.gui;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
/**
 *
 * 类描述： 不间断地扫描输入流 将扫描到的字符流显示在JTextArea上
 *
 * @author 杨胜寒
 * @date 2011-12-20 创建
 * @version 1.0
 */
public class TextAreaLogAppender extends LogAppender {

    private JTextArea textArea;
    private JScrollPane scroll;

    /**
     * 默认的构造
     *
     * @param textArea 记录器名称，该记录器输出的日志信息将被截取并输出到指定的JTextArea组件
     * @param scroll
     * JTextArea组件使用的滚动面板，因为在JTextArea中输出日志时，默认会使垂直滚动条自动向下滚动，若不需要此功能，此参数可省略
     * @throws IOException
     */
    public TextAreaLogAppender(JTextArea textArea, JScrollPane scroll) throws IOException {
        super("textArea");
        this.textArea = textArea;
        this.scroll = scroll;
    }

    @Override
    public void run() {
        // 不间断地扫描输入流  
        Scanner scanner = new Scanner(reader);
        // 将扫描到的字符流输出到指定的JTextArea组件  
        while (scanner.hasNextLine()) {
            try {
                //睡眠  
                String line = scanner.nextLine();
                textArea.append(line);
                textArea.append("\n");
                if(textArea.getLineCount()>4000){
                 textArea.setText("");
                }
                
                line = null;
                //使垂直滚动条自动向下滚动  
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
            } catch (Exception ex) {
                ex.printStackTrace();
                //异常不做处理  
            }
        }
    }
}
