package org.miss.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.intellij.ide.plugins.newui.TextHorizontalLayout;
import com.intellij.json.JsonParser;
import com.intellij.json.json5.Json5Language;
import com.intellij.ui.components.JBList;
import org.miss.redis.component.RedisDBComponent;
import org.miss.redis.component.RedisServerListModel;
import org.miss.redis.service.impl.RedisKeysRender;
import org.miss.redis.service.impl.RedisServerRender;
import org.miss.redis.setting.RedisDb;
import org.miss.redis.setting.RedisDbSetting;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis
 * @author: miss
 * @since: 2020/7/27 15:16
 * @history: 1.2020/7/27 created by miss
 */
public class RedisManager {
    private JPanel contentPanel;
    private JPanel secondPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JList<RedisDBComponent> serverList;
    private JList<String> keyList;
    private JTextPane keyTextPanel;
    private JButton moreButton;
    private JScrollPane keyListPanel;
    private JTextPane ttlTextPanel;
    private JEditorPane valuePanel;
    private JButton updateValueButton;
    private JTextField keyWordText;
    private JButton searchKeyButton;
    private JButton clearKeySearch;
    private JButton newConnectButton;
    private JButton newKeyButton;
    private JPanel valueDetailPanel;
    private JPanel keyDetailPanel;
    private JPanel valueDisplayPanel;
    private JPanel valurViewPanel;
    private JButton updateButton;
    private JList valueList;
    private JFrame frame;

    public RedisManager() {
        frame = new JFrame("RedisManage");
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initRedisServerList();
        frame.pack();
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setVisible(true);
    }

    public void updateServerList(List<RedisDb> allRedisDb) {
//        RedisDBComponent defauleRedisDb = new RedisDBComponent("k8s.definesys.com", 31006, "xdapredis", "xdap_dev_redis_2");

        RedisServerListModel redisServerListModel = new RedisServerListModel();

        for (RedisDb redisDb1 : allRedisDb) {
            redisServerListModel.addElement(new RedisDBComponent(redisDb1));
        }
//        redisServerListModel.addElement(defauleRedisDb);
        serverList.setModel(redisServerListModel);
    }

    public void initRedisServerList() {
//        RedisDBComponent defauleRedisDb = new RedisDBComponent("k8s.definesys.com", 31006, "xdapredis", "xdap_dev_redis_2");
        RedisDbSetting dbSetting = RedisDbSetting.getInstance();
        List<RedisDb> redisDbList = dbSetting.getAllDbList();

        RedisServerListModel redisServerListModel = new RedisServerListModel();

        for (RedisDb redisDb1 : redisDbList) {
            redisServerListModel.addElement(new RedisDBComponent(redisDb1));
        }
//        redisServerListModel.addElement(defauleRedisDb);

        serverList.setModel(redisServerListModel);
        serverList.setCellRenderer(new RedisServerRender());
        serverList.setBorder(BorderFactory.createTitledBorder("redis-server"));
        serverList.addListSelectionListener(e -> {
            RedisDBComponent selectRedisServer = serverList.getSelectedValue();
            selectRedisServer.initJedisSharedInfo();
            Jedis jedis = selectRedisServer.getJedis();
            ScanParams scanParams = new ScanParams();
            scanParams.count(20);
            ScanResult<String> stringScanResult = jedis.scan((selectRedisServer.getRedisCursor() == null || selectRedisServer.getRedisCursor().isEmpty()) ? "0" : selectRedisServer.getRedisCursor());
            selectRedisServer.setRedisCursor(stringScanResult.getCursor());
            selectRedisServer.setRedisKeys(stringScanResult.getResult());
            String[] keyStringArray = new String[stringScanResult.getResult().size()];
            keyList.setListData(selectRedisServer.getRedisKeys().toArray(keyStringArray));

            selectRedisServer.close();

        });
        keyList.setCellRenderer(new RedisKeysRender());
        keyList.setAutoscrolls(true);
        moreButton.addActionListener(this::onMoreAction);
        searchKeyButton.addActionListener(this::onKeySearchAction);
        clearKeySearch.addActionListener(this::onCleanKeyAction);
        updateButton.addActionListener(this::onValueUpdateAction);
        updateValueButton.addActionListener(this::onValueUpdateConfirmAction);
        newConnectButton.addActionListener(this::newConnectAction);
        valueList = new JBList();
        valurViewPanel.setLayout(new GridLayout(1, 1));
        valuePanel = new JEditorPane();
        keyList.addListSelectionListener(this::listSelectionListenerAction);
    }


    private void listSelectionListenerAction(ListSelectionEvent e) {
        valurViewPanel.removeAll();
        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();
        String key = keyList.getSelectedValue();
        if (key == null || key.isEmpty()) {
            return;
        }
        String keyType = jedis.type(key);
        String value = "";
        keyTextPanel.setText(key);
        ttlTextPanel.setText(jedis.ttl(key) + " ms");
        switch (keyType) {
            case "string":
                valurViewPanel.setVisible(true);

                value = jedis.get(key);
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            case "list":

                valueList.setListData(jedis.lrange(key, 0, -1).toArray());
                valurViewPanel.add(valueList);
                valurViewPanel.updateUI();
                break;
            case "set":

                valueList.setListData(jedis.smembers(key).toArray());
                valurViewPanel.add(valueList);
                valurViewPanel.updateUI();
                break;
            case "zset":

                valueList.setListData(jedis.zrange(key, 0, -1).toArray());
                valurViewPanel.add(valueList);
                valurViewPanel.updateUI();
                break;
            case "hash":
                value = jedis.hgetAll(key).toString();
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            default:
                value = "";

                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();

                break;
        }

        selectRedisServer.close();

    }
    private void newConnectAction(ActionEvent actionEvent) {
        RedisConnectionPage form = new RedisConnectionPage(this);

    }

    private void onCleanKeyAction(ActionEvent actionEvent) {
        keyWordText.setText("");

        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();
        ScanParams scanParams = new ScanParams();
        scanParams.count(20);
        ScanResult<String> stringScanResult = jedis.scan((selectRedisServer.getRedisCursor() == null || selectRedisServer.getRedisCursor().isEmpty()) ? "0" : selectRedisServer.getRedisCursor());
        selectRedisServer.setRedisCursor(stringScanResult.getCursor());
        selectRedisServer.setRedisKeys(stringScanResult.getResult());
        String[] keyStringArray = new String[stringScanResult.getResult().size()];
        keyList.setListData(selectRedisServer.getRedisKeys().toArray(keyStringArray));

        selectRedisServer.close();

    }

    private void onKeySearchAction(ActionEvent actionEvent) {
        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();

        if (keyWordText.getText().trim().isEmpty()) {
            return;
        }
        String key = keyWordText.getText().trim();
        Set<String> matchKeys = jedis.keys("*" + key + "*");
        String[] keyStringArray = new String[matchKeys.size()];
        keyList.setListData(matchKeys.toArray(keyStringArray));
        selectRedisServer.close();
    }
    private void onValueUpdateAction(ActionEvent actionEvent) {
        valurViewPanel.removeAll();
        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();
        String key = keyList.getSelectedValue();
        String keyType = jedis.type(key);
        String value = "";
        switch (keyType) {
            case "string":
                valurViewPanel.setVisible(true);

                value = jedis.get(key);
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            case "list":

                value = String.join(",", jedis.lrange(key, 0, -1));
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            case "set":
                value = String.join(",", jedis.smembers(key));
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            case "zset":

                value = String.join(",", jedis.zrange(key, 0, -1));
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            case "hash":
                ObjectMapper mapper = new ObjectMapper();
                try {
                    value = mapper.writeValueAsString(jedis.hgetAll(key));
                }catch (Exception e) {
                    e.printStackTrace();
                }
                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();
                break;
            default:
                value = "";

                valuePanel.setText(value);
                valurViewPanel.add(valuePanel);
                valurViewPanel.updateUI();

                break;
        }


    }
    private void onValueUpdateConfirmAction(ActionEvent actionEvent) {
        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();
        String key = keyList.getSelectedValue();
        if (key == null || key.isEmpty()) {
            return;
        }
        String keyType = jedis.type(key);
        String value = valuePanel.getText().trim();
        switch (keyType) {
            case "string":
                jedis.set(key, value);

                break;
            case "list":
                jedis.del(key);
                jedis.lpush(key, value.split(","));
                break;
            case "set":
                jedis.sadd(key, value.split(","));
                break;
            case "zset":
                jedis.sadd(key, value.split(","));
                break;
            case "hash":
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Map<String, String> valueMap = mapper.readValue(value, Map.class);
                    jedis.hset(key, valueMap);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                value = value;
                break;
        }
        this.listSelectionListenerAction(null);
        System.out.println(value);
        selectRedisServer.close();

    }


    private void onMoreAction(ActionEvent e) {
        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();
        ScanParams scanParams = new ScanParams();
        scanParams.count(20);
        if (!keyWordText.getText().trim().isEmpty()) {
            scanParams.match("*" + keyWordText.getText().trim() + "*");
        }
        ScanResult<String> stringScanResult = jedis.scan((selectRedisServer.getRedisCursor() == null || selectRedisServer.getRedisCursor().isEmpty()) ? "0" : selectRedisServer.getRedisCursor(), scanParams);
        selectRedisServer.setRedisCursor(stringScanResult.getCursor());
        selectRedisServer.getRedisKeys().addAll(stringScanResult.getResult());
        String[] keyStringArray = new String[stringScanResult.getResult().size()];
        keyList.setListData(selectRedisServer.getRedisKeys().toArray(keyStringArray));
        selectRedisServer.close();
    }

}
