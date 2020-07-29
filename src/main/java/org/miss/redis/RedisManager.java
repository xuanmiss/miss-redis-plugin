package org.miss.redis;

import org.jsoup.internal.StringUtil;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static javax.swing.JList.HORIZONTAL_WRAP;

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
    private JPanel keyValuePanel;
    private JTextPane keyTextPanel;
    private JEditorPane valueEditorPanel;
    private JButton 修改;
    private JButton moreButton;
    private JScrollPane keyListPanel;

    public RedisManager() {
        JFrame frame = new JFrame("RedisManage");
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initRedisServerList();
        frame.pack();
        frame.setSize(800, 1000);
        frame.setVisible(true);
        frame.setVisible(true);
    }

    private void initRedisServerList() {
        RedisDBComponent defauleRedisDb = new RedisDBComponent("k8s.definesys.com", 31006, "xdapredis", "xdap_dev_redis_2");
        RedisDbSetting dbSetting = RedisDbSetting.getInstance();
        List<RedisDb> redisDbList = dbSetting.getAllRedisDb();

        RedisServerListModel redisServerListModel = new RedisServerListModel();

        for (RedisDb redisDb1 : redisDbList) {
            redisServerListModel.addElement(new RedisDBComponent(redisDb1));
        }
        redisServerListModel.addElement(defauleRedisDb);

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
        keyTextPanel.setAutoscrolls(true);
        valueEditorPanel.setAutoscrolls(true);
        keyList.addListSelectionListener(e -> {
            RedisDBComponent selectRedisServer = serverList.getSelectedValue();
            selectRedisServer.initJedisSharedInfo();
            Jedis jedis = selectRedisServer.getJedis();
            String key = keyList.getSelectedValue();
            String keyType = jedis.type(key);
            String value = "";
            switch (keyType) {
                case "string":
                    value = jedis.get(key);
                    break;
                case "list":
                    value = jedis.lrange(key, 0, -1).toString();
                    break;
                case "set":
                    value = jedis.smembers(key).toString();
                    break;
                case "zset":
                    value = jedis.zrange(key, 0, -1).toString();
                    break;
                case "hash":
                    value = jedis.hgetAll(key).toString();
                    break;
                default:
                    value = "";
                    break;
            }
            keyTextPanel.setText(key);
            valueEditorPanel.setText(value);
            selectRedisServer.close();
        });

    }

    private void onMoreAction(ActionEvent e) {
        RedisDBComponent selectRedisServer = serverList.getSelectedValue();
        selectRedisServer.initJedisSharedInfo();
        Jedis jedis = selectRedisServer.getJedis();
        ScanParams scanParams = new ScanParams();
        scanParams.count(20);
        ScanResult<String> stringScanResult = jedis.scan((selectRedisServer.getRedisCursor() == null || selectRedisServer.getRedisCursor().isEmpty()) ? "0" : selectRedisServer.getRedisCursor());
        selectRedisServer.setRedisCursor(stringScanResult.getCursor());
        selectRedisServer.getRedisKeys().addAll(stringScanResult.getResult());
        String[] keyStringArray = new String[stringScanResult.getResult().size()];
        keyList.setListData(selectRedisServer.getRedisKeys().toArray(keyStringArray));
        selectRedisServer.close();
    }

}
