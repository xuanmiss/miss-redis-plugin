package org.miss.redis.component;

import com.intellij.openapi.ui.messages.MessageDialog;
import org.miss.redis.client.JedisConnection;
import org.miss.redis.setting.RedisDb;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Objects;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.component
 * @author: miss
 * @since: 2020/7/28 11:05
 * @history: 1.2020/7/28 created by miss
 */
public class RedisDBComponent extends Component implements MouseListener {

    private String host;
    private Integer port;
    private String password;
    private String name;

    private JedisConnection jedisConnection;

    private JedisShardInfo jedisShardInfo;

    private Jedis jedis;

    private String info;

    private String redisCursor;

    private List<String> redisKeys;


    public RedisDBComponent(RedisDb redisDb1) {
        this.host = redisDb1.getHost();
        this.password = redisDb1.getPassword();
        this.name = redisDb1.getDbName();
        this.port = redisDb1.getPort();
    }

    public RedisDBComponent() {
    }

    public RedisDBComponent(String host, Integer port, String password, String name) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.name = name;
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);

    }

    public JedisConnection getJedisConnection() {
        return jedisConnection;
    }

    public JedisShardInfo getJedisShardInfo() {
        return jedisShardInfo;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public String getInfo() {
        return info;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedisCursor() {
        return redisCursor;
    }

    public void setRedisCursor(String redisCursor) {
        this.redisCursor = redisCursor;
    }

    public List<String> getRedisKeys() {
        return redisKeys;
    }

    public void setRedisKeys(List<String> redisKeys) {
        this.redisKeys = redisKeys;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RedisDBComponent)) return false;
        RedisDBComponent that = (RedisDBComponent) o;
        return Objects.equals(host, that.host) &&
                Objects.equals(port, that.port) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, password, name);
    }

//    @Override
//    public String toString() {
//        return name;
//    }


    @Override
    public String toString() {
        return "RedisDBComponent{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", jedisConnection=" + jedisConnection +
                ", jedisShardInfo=" + jedisShardInfo +
                ", info='" + info + '\'' +
                '}';
    }


    public void initJedisSharedInfo() {
        this.jedisConnection = new JedisConnection();
        this.jedisShardInfo = jedisConnection.initJedisSharedInfo(this.host, this.name, this.port, this.password);
        this.jedis = jedisShardInfo.createResource();
        this.info = jedisShardInfo.createResource().info();
    }

    public void close() {
        this.jedis.close();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        MessageDialog dialog = new MessageDialog(info, "redis server", new String[]{"confirm"}, 1, null);
        dialog.setSize(400, 200);
        dialog.show();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
