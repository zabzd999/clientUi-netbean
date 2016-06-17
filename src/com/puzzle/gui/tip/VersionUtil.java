/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puzzle.gui.tip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
//import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
/**
 *
 * @author Administrator
 */
public class VersionUtil{ 
private Map<String, String> feaMap = null;
//private Point oldP;    //上一次坐标,拖动窗口时用
private TipWindow tw = null; //提示框 
//private ImageIcon img = null;//图像组件
//private JLabel imgLabel = null; //背景图片标签
private JPanel headPan = null;
private JPanel feaPan =null;
private JPanel btnPan = null;
private JLabel title = null;
private JLabel head = null;
private JLabel close = null;//关闭按钮
private JTextArea feature = null; 
private JScrollPane jfeaPan = null;
private JLabel releaseLabel = null;
private JLabel update = null;
private SimpleDateFormat sdf=null;


{
   sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   feaMap = new HashMap<String, String>(); 
   feaMap.put("name", "报文转换过程中出错(商品配置信息缺失)");
   feaMap.put("release", sdf.format(new Date()));
//   feaMap.put("feature", "这里是提示框主内容\n  1.代码改进与完善,去掉了许多无用的代码\n  2.自制菜单栏\n  3.增加拖动功能\n  4.自制背景图片\n");
}


public VersionUtil(String content) {
   init( content);   
   handle();
   tw.setAlwaysOnTop(true);    
   tw.setUndecorated(true);
   tw.setResizable(false);
   tw.setVisible(true);
   tw.getRootPane().setWindowDecorationStyle(JRootPane.NONE);//设置此项防止用了beautyeye后setUndecorated失效
   tw.run();
}
public void init(String content){  
  
   //新建300x250的消息提示框
   // Tom, 设置整个提示框大小
   tw = new TipWindow(300, 250);
   // 如果要提示框加上背景图,加上以下2句,把背景图放在项目根目录下
//   img = new ImageIcon("bg_u_all.gif");
//   imgLabel = new JLabel(img);  
   //设置各个面板的布局以及面板中控件的边界
   headPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
   feaPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
   btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
   title = new JLabel("友情提示："); // 提示框标题
//   title = new JLabel("Tom 气泡提示框程序 V1.0"); // 提示框标题
   head = new JLabel(feaMap.get("name"));
   close = new JLabel(" x");//关闭按钮
//   feature = new JTextArea(feaMap.get("feature")); 
   feature = new JTextArea(content); 
   jfeaPan = new JScrollPane(feature);
   releaseLabel = new JLabel("发现日期 " + feaMap.get("release"));  
   update = new JLabel("");
  
   // 将各个面板设置为透明，否则看不到背景图片
   ((JPanel) tw.getContentPane()).setOpaque(false);
   headPan.setOpaque(false);
   feaPan.setOpaque(false);
//   btnPan.setOpaque(false); //Tom, 不要透明
  
   //设置JDialog的整个背景图片
//   tw.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
//   imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
   tw.setBackground(Color.WHITE); //Tom 整个提示框背景
   headPan.setPreferredSize(new Dimension(300, 60));
  
   //设置提示框的边框,宽度和颜色
   tw.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
  
   title.setPreferredSize(new Dimension(260, 26));
   title.setVerticalTextPosition(JLabel.CENTER);
   title.setHorizontalTextPosition(JLabel.CENTER);
   title.setFont(new Font("宋体", Font.PLAIN, 12));
   title.setForeground(Color.black);

  
   close.setFont(new Font("Arial", Font.BOLD, 15));
   close.setPreferredSize(new Dimension(20, 20));
   close.setVerticalTextPosition(JLabel.CENTER);
   close.setHorizontalTextPosition(JLabel.CENTER);
   close.setCursor(new Cursor(12));
   close.setToolTipText("关闭");

   // Tom 内容上面
//   head.setPreferredSize(new Dimension(250, 35));
   head.setPreferredSize(new Dimension(250, 20)); 
   head.setVerticalTextPosition(JLabel.CENTER);
   head.setHorizontalTextPosition(JLabel.CENTER);
   head.setFont(new Font("宋体", Font.PLAIN, 12));
   head.setForeground(Color.blue);

   // Tom 主内容窗口的设置
   feature.setEditable(false);
   feature.setForeground(Color.red);
//   feature.setFont(new Font("宋体", Font.PLAIN, 13));
   feature.setFont(new Font("微软雅黑", Font.PLAIN, 12));
//   feature.setBackground(new Color(184, 230, 172));
   feature.setBackground(Color.WHITE);
   //设置文本域自动换行
   feature.setLineWrap(true);

   // Tom 主内容位置设置
//   jfeaPan.setPreferredSize(new Dimension(250, 80));
   jfeaPan.setPreferredSize(new Dimension(250, 120));
   jfeaPan.setBorder(null);
   jfeaPan.setBackground(Color.black);
  
   releaseLabel.setForeground(Color.DARK_GRAY); 
   releaseLabel.setFont(new Font("宋体", Font.PLAIN, 12));
  
   // 为了隐藏文本域，帮加个空的JLabel将他挤到下面去
   // Tom, 主内容与主内容上方标题的距离
   JLabel jsp = new JLabel();
   jsp.setPreferredSize(new Dimension(300, 10));
  
   update.setPreferredSize(new Dimension(110, 30));
//   //设置标签鼠标手形
//   update.setCursor(new Cursor(12));
//   // Tom 更新按钮
   update.setBackground(Color.BLACK);
//   update.setText("确定");
  

   headPan.add(title);
   headPan.add(close);
//   headPan.add(head);
  
   feaPan.add(jsp);
   feaPan.add(jfeaPan);
   feaPan.add(releaseLabel);
  
   btnPan.add(update);
   // Tom 最底下按钮层颜色
   btnPan.setBackground(Color.LIGHT_GRAY);
//   update.setBackground(Color.CYAN);
  
   tw.add(headPan, BorderLayout.NORTH);
   tw.add(feaPan, BorderLayout.CENTER);
   tw.add(btnPan, BorderLayout.SOUTH);
  
}


public void handle() { 
   //右上角关闭按钮事件
   close.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
     tw.close();
//     System.exit(0);
    }

    public void mouseEntered(MouseEvent e) {
     close.setBorder(BorderFactory.createLineBorder(Color.gray));
    }

    public void mouseExited(MouseEvent e) {
     close.setBorder(null);
    }
   });

}
public static void main(String args[]) throws Exception {
 
    
             BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
//            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);//隐藏设置按钮
    
            new VersionUtil("dss");

}

}
