/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.gui.flash;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;
import java.awt.Color;

/**
 *
 * @author ljs
 */
public class SimpleFlashExample  extends JPanel{
     public SimpleFlashExample() {
    super(new BorderLayout());
    JFlashPlayer flashPlayer = new JFlashPlayer();
    flashPlayer.load(getClass(), "resource/buterfly.swf");
    add(flashPlayer, BorderLayout.CENTER);
  }

  /* Standard main method to try that test as a standalone application. */
  public static void main(String[] args) {
    UIUtils.setPreferredLookAndFeel();
    NativeInterface.open();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("DJ Native Swing Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SimpleFlashExample(), BorderLayout.CENTER);
        frame.setUndecorated(true);
        frame.setSize(800, 600);
        frame.setBackground(Color.yellow);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
      }
    });
    NativeInterface.runEventPump();
  }
}
