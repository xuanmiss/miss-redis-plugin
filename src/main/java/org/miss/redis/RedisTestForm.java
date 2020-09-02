package org.miss.redis;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis
 * @author: miss
 * @since: 2020/9/1 17:22
 * @history: 1.2020/9/1 created by miss
 */
public class RedisTestForm {
    private JPanel mainPanel;
    private JButton button1;
    private JTextPane textPane1;
    JFrame frame;

    public RedisTestForm() {
        frame = new JFrame("RedisTest");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initTestForm();
        frame.pack();
        frame.setSize(800, 1000);
        frame.setVisible(true);

    }

    private void initTestForm() {
         button1.addActionListener(this::addNewPanel);
    }

    private void addNewPanel(ActionEvent actionEvent) {
        JTextPane panel = new JTextPane();
        panel.setText("hahahahahahahahahahahahahahah");
        textPane1.setText("hello world");
        mainPanel.setLayout(new GridLayout(1, 4));

        mainPanel.updateUI();
    }
}
