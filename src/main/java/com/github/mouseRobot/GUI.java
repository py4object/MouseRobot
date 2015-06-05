package com.github.mouseRobot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Omar Zahed on 5/31/15.
 */

public class GUI {
    JFrame mJFrame;
    JButton btExit;
    public GUI(){
        mJFrame =new JFrame("Mouse Robot");
        JPanel rootPanel=new JPanel();
        rootPanel.setLayout(new GridLayout(4,0,0,0));
        mJFrame.add(rootPanel);
        rootPanel.add(new JLabel("Press CTR +ALT record/stop recoding The mouse movement"));
        rootPanel.add(new JLabel("Press CTR+Shift to preform/stop the recorded movment "));
        btExit=new JButton("Exit");
        mJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        rootPanel.add(btExit);
        mJFrame.pack();
        mJFrame.setVisible(true);

    }
}
