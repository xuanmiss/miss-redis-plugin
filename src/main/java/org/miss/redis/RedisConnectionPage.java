package org.miss.redis;

import com.intellij.openapi.ui.messages.MessageDialog;
import org.miss.redis.setting.RedisDb;
import org.miss.redis.setting.RedisDbSetting;
import org.miss.redis.utils.RedisUtils;

import javax.swing.*;
import java.util.Collections;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis
 * @author: miss
 * @since: 2020/7/27 15:57
 * @history: 1.2020/7/27 created by miss
 */
public class RedisConnectionPage {
    private JPanel rootPane;
    private JTextField nameTextField;
    private JTextField hostTextField;
    private JTextField portTextField;
    private JButton testConnectButton;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPasswordField passwordField;

    private RedisManager redisManager;

    public RedisConnectionPage() {
        JFrame frame = new JFrame("RedisConnection");
        frame.setContentPane(rootPane);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 600);
        frame.setVisible(true);
        frame.addWindowStateListener(windowEvent -> {
            System.out.println(windowEvent.getNewState());
            System.out.println(windowEvent.getOldState());
        });

        cancelButton.addActionListener(e -> {
            JFrame jFrame = frame;
            onCancel(jFrame);
        });

        confirmButton.addActionListener(e -> {
            JFrame jFrame = frame;
            onConfirm(jFrame);
        });

        testConnectButton.addActionListener(e -> {
            onTestConnection();
        });
    }

    public RedisConnectionPage(RedisManager redisManager) {
        JFrame frame = new JFrame("RedisConnection");
        frame.setContentPane(rootPane);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 600);
        frame.setVisible(true);
        frame.addWindowStateListener(windowEvent -> {
            System.out.println(windowEvent.getNewState());
            System.out.println(windowEvent.getOldState());
        });

        cancelButton.addActionListener(e -> {
            JFrame jFrame = frame;
            onCancel(jFrame);
        });

        confirmButton.addActionListener(e -> {
            JFrame jFrame = frame;
            onConfirm(jFrame);
        });

        testConnectButton.addActionListener(e -> {
            onTestConnection();
        });
        this.redisManager = redisManager;
    }

    private void onTestConnection() {
        String host = hostTextField.getText().trim();

        Integer port = Integer.valueOf(portTextField.getText().trim());

        String password = String.valueOf(passwordField.getPassword()).trim();

        String redisServerName = nameTextField.getText().trim();

        boolean connectStatus = RedisUtils.connectStatus(host, port, password, redisServerName);
        if (!connectStatus) {
            MessageDialog dialog = new MessageDialog("redis connection error", "connection fail", new String[]{"confirm"}, 1, null);
            dialog.setSize(400, 200);
            dialog.show();

        }
    }

    private void onConfirm(JFrame jFrame) {
        String host = hostTextField.getText().trim();

        Integer port = Integer.valueOf(portTextField.getText().trim());

        String password = String.valueOf(passwordField.getPassword()).trim();

        String redisServerName = nameTextField.getText().trim();

        if (RedisUtils.connectStatus(host, port, password, redisServerName)) {
            RedisDbSetting db = RedisDbSetting.getInstance();
            if (null == db) {

                MessageDialog dialog = new MessageDialog("add server", "server is not up", new String[]{"cancel", "confirm"}, 1, null);
                dialog.setSize(600, 200);
                dialog.show();
            } else {
                RedisDb redisDb = new RedisDb(host, port, password, redisServerName);
                if (db.isRedisDbExit(redisDb)) {
                    MessageDialog dialog = new MessageDialog("server exist", "server repeat", new String[]{"confirm"}, 1, null);
                    dialog.setSize(600, 200);
                    dialog.show();
                } else {
                    db.addRedisDb(new RedisDb(host, port, password, redisServerName));
                    redisManager.updateServerList(db.getAllDbList());
                    jFrame.dispose();
                }
            }
        }

    }

    private void onCancel(JFrame jFrame) {
        jFrame.dispose();
    }

}
