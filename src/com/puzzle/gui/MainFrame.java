/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.gui;

import com.hnblc.sign.api.ClientApi;
import com.hnblc.sign.util.UkeyJnaAPIHelper;
import com.puzzle.module.send.listener.startSendAndReceDirectoryMonitor;

import com.puzzle.util.DateUtil;
import com.puzzle.util.FileDto;
import com.puzzle.util.JtableMessageDto;
import com.puzzle.util.QueueUtils;
import com.puzzle.util.UkeyLoginDto;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

/**
 *
 * @author Administrator
 */
public class MainFrame extends JFrame {

    private static Logger log = Logger.getLogger(MainFrame.class.getName());

    /**
     * 渲染table的奇偶行；设计出斑马纹
     *
     * @param table
     */
    public static void makeJtableFace(javax.swing.JTable table) {

        try {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,
                        Object value, boolean isSelected, boolean hasFocus,
                        int row, int column) {
                    if (row % 2 == 0) {
                        setBackground(java.awt.Color.white); //设置奇数行底色
                    } else if (row % 2 == 1) {
                        setBackground(new java.awt.Color(206, 231, 255)); //设置偶数行底色
                    }
                    return super.getTableCellRendererComponent(table, value,
                            isSelected, hasFocus, row, column);
                }
            };
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {

        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/images/flag.png")));
        jLabel1.setText("");
        jLabel2.setText("");
        jLabel5.setText("");
        jLabel4.setText("");
        makeJtableFace(jTable1);
        makeJtableFace(jTable2);
        makeJtableFace(jTable3);
        makeJtableFace(jTable4);
        this.setLocationRelativeTo(null);

        jButton1.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        jButton2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        this.jDialog1.setSize(600, 480);
        this.jDialog1.setTitle("title");

        this.jDialog2.setSize(800, 540);
        this.jDialog2.setIconImage(Toolkit.getDefaultToolkit().getImage(""));
        this.jDialog1.setIconImage(Toolkit.getDefaultToolkit().getImage(""));
        jDialog1.setLocationRelativeTo(this);
        jDialog2.setLocationRelativeTo(this);

          jLabel6.setVisible(false);
        this.jDialog1.setVisible(false);
        tray();
    }

    /**
     * checkJlabel
     */
    public void setcheckJlabelGreen(JLabel label) {
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/11.png")));
    }

    public void setcheckJlabelWhite(JLabel label) {
        label.setIcon(null);
    }

    public void setcheckJlabelRed(JLabel label) {
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2.png")));
    }

    public class setScheckJlabel1Status extends Thread {

        public void run() {
            while (true) {

                try {
                    Thread.sleep(150);
                    if (jCheckBox8.isSelected()) {
                        setcheckJlabelWhite(jLabel1);
                        Thread.sleep(270);
                        setcheckJlabelGreen(jLabel1);

                    } else {
                        setcheckJlabelRed(jLabel1);
                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    log.error(ex.getMessage(), ex);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }

            }

        }
    }

    public class setScheckJlabel4Status extends Thread {

        public void run() {
            while (true) {

                try {
                    Thread.sleep(150);
                    if (jCheckBox12.isSelected()) {
                        setcheckJlabelWhite(jLabel4);
                        Thread.sleep(270);

                        setcheckJlabelGreen(jLabel4);

                    } else {
                        setcheckJlabelRed(jLabel4);
                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    log.error(ex.getMessage(), ex);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }

            }

        }
    }

    public class setScheckJlabel2Status extends Thread {

        public void run() {
            while (true) {

                try {
                    Thread.sleep(200);
                    if (jCheckBox7.isSelected()) {
                        setcheckJlabelWhite(jLabel2);
                        Thread.sleep(130);
                        setcheckJlabelGreen(jLabel2);

                    } else {
                        setcheckJlabelRed(jLabel2);
                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    log.error(ex.getMessage(), ex);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }

            }

        }
    }

    public class setScheckJlabel5Status extends Thread {

        public void run() {
            while (true) {

                try {
                    Thread.sleep(200);
                    if (jCheckBox11.isSelected()) {

                        setcheckJlabelWhite(jLabel5);
                        Thread.sleep(130);

                        setcheckJlabelGreen(jLabel5);

                    } else {

                        setcheckJlabelRed(jLabel5);
                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    log.error(ex.getMessage(), ex);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }

            }

        }
    }

    public class monitorErrorPathListener extends FileAlterationListenerAdaptor {

        public MainFrame f;

        public monitorErrorPathListener(MainFrame f) {
            this.f = f;
        }

        @Override
        public void onFileCreate(File file) {
            super.onFileCreate(file);
            DefaultTableModel m = (DefaultTableModel) f.jTable2.getModel();
            int i = m.getRowCount() + 1;
            m.addRow(new Object[]{i, file.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())});
            int roucount = m.getRowCount();
            if ((roucount % 1000) == 0) {
                m.setRowCount(0);
            }
        }
    }

//    public class monitorReceCusPathListener extends FileAlterationListenerAdaptor {
//
//        public MainFrame f;
//
//        public monitorReceCusPathListener(MainFrame f) {
//            this.f = f;
//        }
//
//        @Override
//        public void onFileCreate(File file) {
//            super.onFileCreate(file);
//            DefaultTableModel m = (DefaultTableModel) f.getjTable3().getModel();
//            int i = m.getRowCount() + 1;
//            m.addRow(new Object[]{String.valueOf(i), file.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())});
//            int roucount = m.getRowCount();
//
//            if ((roucount % 1000) == 0) {
//                m.setRowCount(0);
//            }
//        }
//    }
//    public class monitorReceCiqPathListener extends FileAlterationListenerAdaptor {
//
//        public MainFrame f;
//
//        public monitorReceCiqPathListener(MainFrame f) {
//            this.f = f;
//        }
//
//        @Override
//        public void onFileCreate(File file) {
//            super.onFileCreate(file);
//            DefaultTableModel m = (DefaultTableModel) f.getjTable3().getModel();
////               m.setRowCount(m.getRowCount()+1); 
//            int i = m.getRowCount() + 1;
//            m.addRow(new Object[]{i, file.getAbsolutePath(), DateUtil.getFormatDateTime(new Date())});
//            int roucount = m.getRowCount();
//            if ((roucount % 1000) == 0) {
//                m.setRowCount(0);
//            }
//        }
//    }
    public void initLog() {
        try {
            Thread t2;
            t2 = new TextAreaLogAppender(jTextArea1, jScrollPane3);
            t2.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("绑定日志输出组件错误");
//            JOptionPane.showMessageDialog(this, e, "绑定日志输出组件错误", JOptionPane.ERROR_MESSAGE);  
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jDialog1 = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jDialog2 = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane_sendmessage = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabbedPane_recemessage = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        set_send_panel = new javax.swing.JPanel();
        jSplitPane6 = new javax.swing.JSplitPane();
        jPanel13 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        set_rece_panel = new javax.swing.JPanel();
        jSplitPane7 = new javax.swing.JSplitPane();
        jPanel16 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

        jDialog1.setAlwaysOnTop(true);
        jDialog1.setUndecorated(true);
        jDialog1.setOpacity(0.9F);
        jDialog1.setType(java.awt.Window.Type.POPUP);
        jDialog1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDialog1MouseClicked(evt);
            }
        });
        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                jDialog1WindowClosed(evt);
            }
        });
        jDialog1.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setPreferredSize(new java.awt.Dimension(550, 400));
        jPanel6.setLayout(new java.awt.BorderLayout());
        jDialog1.getContentPane().add(jPanel6);

        jDialog2.setAlwaysOnTop(true);
        jDialog2.setMinimumSize(new java.awt.Dimension(800, 520));
        jDialog2.setUndecorated(true);
        jDialog2.setOpacity(0.8F);
        jDialog2.setResizable(false);
        jDialog2.setType(java.awt.Window.Type.POPUP);
        jDialog2.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel7.setPreferredSize(new java.awt.Dimension(550, 400));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("正在识别Ukey设备,请勿往发送目录放文件！！！");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 500, -1));

        jDialog2.getContentPane().add(jPanel7);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("跨境贸易单一窗口客户端V1.0");
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(800, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane3.setDividerLocation(56);
        jSplitPane3.setDividerSize(0);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setEnabled(false);

        jPanel1.setAlignmentX(0.1F);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jButton1.setText("文件发送");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setLabel("文件接收");
        jButton2.setMaximumSize(new java.awt.Dimension(360, 50));
        jButton2.setMinimumSize(new java.awt.Dimension(360, 50));
        jButton2.setPreferredSize(new java.awt.Dimension(360, 50));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        jSplitPane3.setLeftComponent(jPanel1);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(0);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setMinimumSize(new java.awt.Dimension(1, 1));
        jSplitPane2.setOneTouchExpandable(true);

        jPanel4.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel4.setLayout(new javax.swing.OverlayLayout(jPanel4));

        jTabbedPane_sendmessage.setName(""); // NOI18N

        jScrollPane1.setName(""); // NOI18N

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "编号", "文件名称", "发送时间"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jTabbedPane_sendmessage.addTab("国检发送列表", jScrollPane1);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "编号", "文件名称", "发送时间"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(100);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jTabbedPane_sendmessage.addTab("海关发送列表", jScrollPane2);

        jPanel4.add(jTabbedPane_sendmessage);
        jTabbedPane_sendmessage.getAccessibleContext().setAccessibleName("");

        jTabbedPane_recemessage.setName(""); // NOI18N

        jScrollPane4.setName(""); // NOI18N

        jTable3.setAutoCreateRowSorter(true);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "编号", "文件名称", "时间"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(10);
        }

        jTabbedPane_recemessage.addTab("海关接收列表", jScrollPane4);

        jTable4.setAutoCreateRowSorter(true);
        jTable4.setBackground(new java.awt.Color(240, 240, 255));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "编号", "文件名称", "时间"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.getTableHeader().setReorderingAllowed(false);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(10);
        }

        jTabbedPane_recemessage.addTab("国检接收列表", jScrollPane5);

        jPanel4.add(jTabbedPane_recemessage);

        jSplitPane2.setTopComponent(jPanel4);

        jPanel5.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(146, 30));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane3.setMinimumSize(new java.awt.Dimension(146, 30));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(146, 30));

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 255, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane3.setViewportView(jTextArea1);

        jPanel5.add(jScrollPane3);

        jSplitPane2.setRightComponent(jPanel5);

        jPanel3.add(jSplitPane2);

        jSplitPane1.setRightComponent(jPanel3);

        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204))));
        jPanel2.setLayout(new javax.swing.OverlayLayout(jPanel2));

        set_send_panel.setPreferredSize(new java.awt.Dimension(199, 385));
        set_send_panel.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane6.setDividerLocation(150);
        jSplitPane6.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "通道设置", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField5.setText("文件根目录");
        jTextField5.setToolTipText("请选择你报文所在的目录");
        jTextField5.setAutoscrolls(false);
        jTextField5.setEnabled(false);
        jTextField5.setPreferredSize(new java.awt.Dimension(6, 23));
        jPanel13.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 118, -1));

        jButton8.setText("...");
        jButton8.setToolTipText("请选择发送目录");
        jButton8.setIconTextGap(0);
        jButton8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton8.setMaximumSize(new java.awt.Dimension(50, 19));
        jButton8.setMinimumSize(new java.awt.Dimension(50, 19));
        jButton8.setPreferredSize(new java.awt.Dimension(50, 19));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 30, 23));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "选择企业类型...", "电商企业", "物流企业", "支付企业", "报关企业" }));
        jComboBox1.setToolTipText("请选择企业类型");
        jComboBox1.setMinimumSize(new java.awt.Dimension(98, 20));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel13.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.gif"))); // NOI18N
        jLabel6.setText("加载中");
        jPanel13.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 105, 60, 30));
        jLabel6.getAccessibleContext().setAccessibleName("");

        jSplitPane6.setLeftComponent(jPanel13);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "发送通道监控", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        jCheckBox7.setSelected(true);
        jCheckBox7.setText("海关");
        jCheckBox7.setToolTipText("只接收海关回执");
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });

        jCheckBox8.setSelected(true);
        jCheckBox8.setText("国检");
        jCheckBox8.setToolTipText("只接收国检回执");
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jCheckBox8))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox7))
                .addContainerGap(189, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel14);

        jSplitPane6.setRightComponent(jPanel15);

        set_send_panel.add(jSplitPane6);

        jPanel2.add(set_send_panel);

        set_rece_panel.setPreferredSize(new java.awt.Dimension(199, 385));
        set_rece_panel.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane7.setDividerLocation(150);
        jSplitPane7.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "通道设置", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField7.setText("海关回执根目录");
        jTextField7.setToolTipText("请选择海关回执目录");
        jTextField7.setAutoscrolls(false);
        jTextField7.setEnabled(false);
        jTextField7.setPreferredSize(new java.awt.Dimension(6, 23));
        jPanel16.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 118, -1));

        jButton10.setText("...");
        jButton10.setIconTextGap(0);
        jButton10.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton10.setMaximumSize(new java.awt.Dimension(50, 19));
        jButton10.setMinimumSize(new java.awt.Dimension(50, 19));
        jButton10.setPreferredSize(new java.awt.Dimension(50, 19));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 30, 23));

        jTextField8.setText("国检回执根目录");
        jTextField8.setToolTipText("请选择国检回执目录");
        jTextField8.setAutoscrolls(false);
        jTextField8.setEnabled(false);
        jTextField8.setPreferredSize(new java.awt.Dimension(6, 23));
        jPanel16.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 118, -1));

        jButton11.setText("...");
        jButton11.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton11.setMaximumSize(new java.awt.Dimension(50, 19));
        jButton11.setMinimumSize(new java.awt.Dimension(50, 19));
        jButton11.setPreferredSize(new java.awt.Dimension(50, 19));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 30, 23));

        jSplitPane7.setLeftComponent(jPanel16);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "接收通道监控", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel17.setLayout(new java.awt.GridLayout(1, 0));

        jCheckBox11.setSelected(true);
        jCheckBox11.setText("海关");
        jCheckBox11.setToolTipText("只接收海关回执");
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });

        jCheckBox12.setSelected(true);
        jCheckBox12.setText("国检");
        jCheckBox12.setToolTipText("只接收国检回执");
        jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox12ActionPerformed(evt);
            }
        });

        jLabel4.setText("jLabel1");

        jLabel5.setText("jLabel2");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox12)
                    .addComponent(jCheckBox11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jCheckBox12))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox11))
                .addContainerGap(189, Short.MAX_VALUE))
        );

        jPanel17.add(jPanel20);

        jSplitPane7.setRightComponent(jPanel17);

        set_rece_panel.add(jSplitPane7);

        jPanel2.add(set_rece_panel);

        jSplitPane1.setLeftComponent(jPanel2);

        jSplitPane3.setRightComponent(jSplitPane1);

        getContentPane().add(jSplitPane3);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int rowNumber = this.jTable3.getSelectedRow();
            String fileName = (String) this.jTable3.getValueAt(rowNumber, 1);

            String content = null;
            try {
                content = org.apache.commons.io.FileUtils.readFileToString(new File(fileName), "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//              String ll=this.readXML(workpath,ff);
            this.jDialog1.setTitle(fileName);
            //代码折叠，语法高亮

            textArea.setText(content);
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            textArea.setCodeFoldingEnabled(true);
            textArea.setAutoscrolls(true);
//            textArea.setBackground(Color.black);
            textArea.setEditable(false);
//             textArea.setVisible(true);
            this.jPanel6.add(sp);

              this.jDialog1.setLocationRelativeTo(this);
            this.jDialog1.setVisible(true);
    }//GEN-LAST:event_jTable3MouseClicked
    }
    private void jDialog1WindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowClosed

// TODO add your handling code here:
        jDialog1.removeAll();
    }//GEN-LAST:event_jDialog1WindowClosed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int rowNumber = this.jTable1.getSelectedRow();
            String fileName = (String) this.jTable1.getValueAt(rowNumber, 1);
            String[] names = fileName.split("\\\\");
            String ff = names[names.length - 1];
            fileName = FileDto.getSingleInstance().fileSendBakPath + File.separator + DateUtil.getCurrDateMM() + File.separator + ff;
            String content = null;
            try {
                content = org.apache.commons.io.FileUtils.readFileToString(new File(fileName), "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
//              String ll=this.readXML(workpath,ff);
            this.jDialog1.setTitle(fileName);
            //代码折叠，语法高亮
            textArea.setText(content);
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            textArea.setCodeFoldingEnabled(true);
            textArea.setAutoscrolls(true);
//            textArea.setBackground(Color.black);
            textArea.setEditable(false);
//             textArea.setVisible(true);
            this.jPanel6.add(sp);

             this.jDialog1.setLocationRelativeTo(this);
            this.jDialog1.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    public static class UkeyC extends Thread {

        public void run() {
            log.info("★★★★★Ukey设备读取中★★★★★★");
            ClientApi api = ClientApi.getSingletonClientApi();
//            String ukeyPasswd = UkeyUtils.Ukey.getProperty("Ukey.passwd");
//            boolean status = api.validUkeyPasswd(ukeyPasswd);//若识别Ukey和验证口令失败则不能发送接收
            UkeyJnaAPIHelper help = api.getSingletonHelp();
            if (help == null) {
                UkeyLoginDto.islogin = false;
                log.info("Ukey认证失败请重新启动软件");
                return;

            }

            log.info("★★★★★Ukey设备验证通过★★★★★★");
            UkeyLoginDto.islogin = true;

        }
    }


    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int rowNumber = this.jTable2.getSelectedRow();
            String fileName = (String) this.jTable2.getValueAt(rowNumber, 1);

            String content = null;
            try {
                content = org.apache.commons.io.FileUtils.readFileToString(new File(fileName), "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
//              String ll=this.readXML(workpath,ff);
            this.jDialog1.setTitle(fileName);
            //代码折叠，语法高亮
            textArea.setText(content);
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            textArea.setCodeFoldingEnabled(true);
            textArea.setAutoscrolls(true);
//            textArea.setBackground(Color.black);
            textArea.setEditable(false);
//             textArea.setVisible(true);
            this.jPanel6.add(sp);

              this.jDialog1.setLocationRelativeTo(this);
            this.jDialog1.setVisible(true);
        }


    }//GEN-LAST:event_jTable2MouseClicked

    private void jDialog1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            if (jDialog1.getSize().width < 900) {
                jDialog1.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
                jDialog1.setLocationRelativeTo(null);
            } else {
                jDialog1.setSize(800, 540);
                jDialog1.setLocationRelativeTo(null);
            }

        }

    }//GEN-LAST:event_jDialog1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        String comboxValue = (String) jComboBox1.getSelectedItem();
        if (StringUtils.isBlank(comboxValue) || "选择企业类型...".equals(comboxValue)) {
            JOptionPane.showMessageDialog(this, "请先选择企业类型");

//             JOptionPane.showMessageDialog(this, evt);
            return;

        }

        int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == jFileChooser1.APPROVE_OPTION) {
            String fileName = jFileChooser1.getSelectedFile().getAbsolutePath();
            if (StringUtils.isBlank(fileName)) {
                return;
            }

            log.info("选择的输入目录是：" + fileName);
            jTextField5.setText(fileName);
            jTextField5.setToolTipText(fileName);

            FileDto.getSingleInstance().fileName = fileName;

            if (fileName == null || fileName.trim().equals("")) {
                log.info("所选目录为空");
                return;
            }

            
            FileDto.getSingleInstance().type =convertStr(comboxValue);
            if (StringUtils.isBlank(FileDto.getSingleInstance().type)) {
                return;
            }
            FileDto.getSingleInstance().reloadConfi(fileName, FileDto.getSingleInstance().type);

//            jDialog2.setLocationRelativeTo(this);
//            jDialog2.setVisible(true);

//            new UkeyC().run();

            new SwingWorker<String, Void>() {

                @Override
                protected String doInBackground() throws Exception {
                    jLabel6.setVisible(true);
                    jComboBox1.setEnabled(false);
                    jButton8.setEnabled(false);

                    new  UkeyC().run();
                    
                    return "1";//返回取到的数据
                }

                @Override
                protected void done() {
                     jLabel6.setVisible(false);
                      
                   if (UkeyLoginDto.islogin) {
                startSendAndReceDirectoryMonitor.startMonitor();
                       jButton8.setEnabled(false);
                       jComboBox1.setEnabled(false);
                      }
                    
                }
            }.execute();

            
//                   if (UkeyLoginDto.islogin) {
//                startSendAndReceDirectoryMonitor.startMonitor();
//                jButton8.setEnabled(false);
//            }
     
//            jDialog2.setVisible(false);
//            jComboBox1.setEnabled(false);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    
    public String convertStr(String value){
    if("电商企业".equals(value))  return  "1";
    if("物流企业".equals(value))  return  "2";
    if("支付企业".equals(value))  return  "3";
    if("报关企业".equals(value))  return  "4";
        
      return null;  
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane_sendmessage.setVisible(true);
        jTabbedPane_recemessage.setVisible(false);
        set_rece_panel.setVisible(false);
        set_send_panel.setVisible(true);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTabbedPane_sendmessage.setVisible(false);
        jTabbedPane_recemessage.setVisible(true);
        set_rece_panel.setVisible(true);
        set_send_panel.setVisible(false);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int rowNumber = this.jTable4.getSelectedRow();
            String fileName = (String) this.jTable4.getValueAt(rowNumber, 1);

            String content = null;
            try {
                content = org.apache.commons.io.FileUtils.readFileToString(new File(fileName), "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//              String ll=this.readXML(workpath,ff);
            this.jDialog1.setTitle(fileName);
            //代码折叠，语法高亮

            textArea.setText(content);
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            textArea.setCodeFoldingEnabled(true);
            textArea.setAutoscrolls(true);
//            textArea.setBackground(Color.black);
            textArea.setEditable(false);
//             textArea.setVisible(true);
            this.jPanel6.add(sp);

              this.jDialog1.setLocationRelativeTo(this);
            this.jDialog1.setVisible(true);
        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
        // TODO add your handling code here:
        
        if(jCheckBox7.isSelected()){
        
            log.info("已开始向海关发送");
        }else{
            log.info("已终止向海关发送");
        }
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox8.isSelected()){
        
            log.info("已开始向国检发送");
        }else{
            log.info("已终止向国检发送");
        }
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
        // TODO add your handling code here:
          
        if(jCheckBox11.isSelected()){
        
            log.info("已开始接收海关回执");
        }else{
            log.info("已终止接收海关回执");
        }
    }//GEN-LAST:event_jCheckBox11ActionPerformed

    private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox12ActionPerformed
        // TODO add your handling code here:
        
        if(jCheckBox12.isSelected()){
        
            log.info("已开始接收国检回执");
        }else{
            log.info("已终止接收国检回执");
        }
    }//GEN-LAST:event_jCheckBox12ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
           String comboxValue =    (String) jComboBox1.getItemAt(0);
           if(StringUtils.isBlank(comboxValue)||comboxValue.equals("选择企业类型...")){
                  jComboBox1.removeItemAt(0);
           }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
         int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == jFileChooser1.APPROVE_OPTION) {
            String fileName = jFileChooser1.getSelectedFile().getAbsolutePath();
            if (StringUtils.isBlank(fileName)) {
                return;
            }

            log.info("选择的输入目录是：" + fileName);
            jTextField7.setText(fileName);
            jTextField7.setToolTipText(fileName);

            FileDto.getSingleInstance().fileReceCusPath = fileName;

 

     
        }
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
                 int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == jFileChooser1.APPROVE_OPTION) {
            String fileName = jFileChooser1.getSelectedFile().getAbsolutePath();
            if (StringUtils.isBlank(fileName)) {
                return;
            }

            log.info("选择的输入目录是：" + fileName);
            jTextField8.setText(fileName);
            jTextField8.setToolTipText(fileName);

            FileDto.getSingleInstance().fileReceCiqPath = fileName;

 

     
        }
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
             this.setVisible(false);//使窗口不可视  
              this.setExtendedState(ICONIFIED);
            this.dispose();//释放当前窗体资源  
    }//GEN-LAST:event_formWindowIconified

    public void startJtableFlushThread() {
        new flushSendTable1Thread().start();
        new flushSendErrorTable2Thread().start();
        new flushReceCusTable3Thread().start();
        new flushReceCiqTable4Thread().start();
    }

    /**
     * 刷新申城申报单列表的线程
     */
    public class flushSendTable1Thread extends Thread {

        public void run() {
            DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
            m.setRowCount(0);
            while (true) {
                try {
                    JtableMessageDto sendMessage = QueueUtils.sendTable1.poll(1, TimeUnit.SECONDS);
                    if (sendMessage == null) {
                        continue;
                    }

                    int is = m.getRowCount() + 1;
                    m.addRow(new Object[]{is, sendMessage.getFileName(), sendMessage.getFileTime()});

                    int i = m.getRowCount();
                    if (i % 1000 == 0) {
                        m.setRowCount(0);
                    }

                } catch (InterruptedException ex) {
                    log.error(ex.getMessage(), ex);
                }

            }
        }
    }

    public class flushSendErrorTable2Thread extends Thread {

        public void run() {
            DefaultTableModel m = (DefaultTableModel) jTable2.getModel();
            m.setRowCount(0);
            while (true) {
                try {
                    JtableMessageDto sendMessage = QueueUtils.sendSignTable.poll(1, TimeUnit.SECONDS);
                    if (sendMessage == null) {
                        continue;
                    }

                    int is = m.getRowCount() + 1;
                    m.addRow(new Object[]{is, sendMessage.getFileName(), sendMessage.getFileTime()});
                    int i = m.getRowCount();
                    if (i % 1000 == 0) {
                        m.setRowCount(0);
                    }

                } catch (InterruptedException ex) {
                    log.error(ex.getMessage(), ex);
                }

            }
        }
    }

    public class flushReceCusTable3Thread extends Thread {

        public void run() {
            DefaultTableModel m = (DefaultTableModel) jTable3.getModel();
            m.setRowCount(0);
            while (true) {
                try {
                    JtableMessageDto sendMessage = QueueUtils.receCusTable3.poll(1, TimeUnit.SECONDS);
                    if (sendMessage == null) {
                        continue;
                    }

                    System.out.println("接收到海关-------");
                    int is = m.getRowCount() + 1;
                    m.addRow(new Object[]{is, sendMessage.getFileName(), sendMessage.getFileTime()});
                    int i = m.getRowCount();
                    if (i % 1000 == 0) {
                        m.setRowCount(0);
                    }

                } catch (InterruptedException ex) {
                    log.error(ex.getMessage(), ex);
                }

            }
        }
    }

    public class flushReceCiqTable4Thread extends Thread {

        public void run() {
            DefaultTableModel m = (DefaultTableModel) jTable4.getModel();
            m.setRowCount(0);
            while (true) {
                try {
                    JtableMessageDto sendMessage = QueueUtils.receCiqTable4.poll(1, TimeUnit.SECONDS);
                    if (sendMessage == null) {
                        continue;
                    }

                    int is = m.getRowCount() + 1;
                    m.addRow(new Object[]{is, sendMessage.getFileName(), sendMessage.getFileTime()});
                    int i = m.getRowCount();
                    if (i % 1000 == 0) {
                        m.setRowCount(0);
                    }

                } catch (InterruptedException ex) {
                    log.error(ex.getMessage(), ex);
                }

            }
        }
    }

    /**
     * UIManager中UI字体相关的key
     */
    public static void setfont() {
        String[] DEFAULT_FONT = new String[]{
            "Table.font", "TableHeader.font", "CheckBox.font", "Tree.font", "Viewport.font", "ProgressBar.font", "RadioButtonMenuItem.font", "ToolBar.font", "ColorChooser.font", "ToggleButton.font", "Panel.font", "TextArea.font", "Menu.font", "TableHeader.font" // ,"TextField.font"
            , "OptionPane.font", "MenuBar.font", "Button.font", "Label.font", "PasswordField.font", "ScrollPane.font", "MenuItem.font", "ToolTip.font", "List.font", "EditorPane.font", "Table.font", "TabbedPane.font", "RadioButton.font", "CheckBoxMenuItem.font", "TextPane.font", "PopupMenu.font", "TitledBorder.font", "ComboBox.font"
        };
// 调整默认字体
        for (int i = 0; i < DEFAULT_FONT.length; i++) {
            UIManager.put(DEFAULT_FONT[i], new Font("微软雅黑", Font.PLAIN, 14));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {

//        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + "kafka" + File.separator + "kafka_client_jaas.conf";
        String path = System.getProperty("user.dir") + File.separator + "config" +  File.separator + "kafka_client_jaas.conf";
        System.setProperty("java.security.auth.login.config", path);

        if (!FileLockUtil.getFileLock()) {
            Jdialog2.showDialog();
            return;
        }

        //设置本属性将改变窗口边框样式定义
        System.setProperty("sun.java2d.noddraw", "true");
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

        UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 1, 2, 20));
        UIManager.put("RootPane.setupButtonVisible", false);
        setfont();
        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame ff = new MainFrame();
                ff.initLog();
                log.info("启动成功");
                ff.setVisible(true);
              
                ff.startJtableFlushThread();//启动刷新列表线程
                ff.new setScheckJlabel2Status().start();
                ff.new setScheckJlabel1Status().start();
                ff.new setScheckJlabel4Status().start();
                ff.new setScheckJlabel5Status().start();
                ff.jButton1ActionPerformed(null);
//                  new UkeyC().start();

                  JWindowShow.flag1 = true;
            }
        });
 
    }

    RSyntaxTextArea textArea = new RSyntaxTextArea(35, 104);
    RTextScrollPane sp = new RTextScrollPane(textArea);

    public void showMouseClick(ActionEvent evt) {
        this.setExtendedState(JFrame.NORMAL);//设置状态为正常  
        this.setVisible(true);

    }

    public void exitMouseClick(ActionEvent evt) {
        tray.remove(trayIcon);//删除系统菜单图标--------------ljs
        System.exit(0);

    }

    public void trayIconMouseClick(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)//鼠标双击图标  
        {
            this.setExtendedState(JFrame.NORMAL);//设置状态为正常  
            this.setVisible(true);//显示主窗体  
        }
    }

    public void tray() {//-----ljs
        tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例     
        PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单  
        final MenuItem show = new MenuItem("打开程序");
        final MenuItem exit = new MenuItem("退出程序");
        pop.add(show);
        pop.add(exit);
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMouseClick(e);
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitMouseClick(e);
            }
        });

        trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/images/flag.png")), "跨境贸易单一窗口客户端V1.0", pop);//实例化托盘图标  
        trayIcon.setImageAutoSize(true);
        //为托盘图标监听点击事件  
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                trayIconMouseClick(e);
            }
        });

        try {
            tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中  
        } catch (AWTException ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }

    }

    TrayIcon trayIcon; // 托盘图标  //------ljs
    SystemTray tray; // 本操作系统托盘的实例  //------ljs
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane6;
    private javax.swing.JSplitPane jSplitPane7;
    private javax.swing.JTabbedPane jTabbedPane_recemessage;
    private javax.swing.JTabbedPane jTabbedPane_sendmessage;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel set_rece_panel;
    private javax.swing.JPanel set_send_panel;
    // End of variables declaration//GEN-END:variables
}
