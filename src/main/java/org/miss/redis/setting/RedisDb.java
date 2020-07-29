package org.miss.redis.setting;

import com.intellij.util.xmlb.annotations.Tag;

import java.util.Objects;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.component
 * @author: miss
 * @since: 2020/7/27 15:35
 * @history: 1.2020/7/27 created by miss
 */

public class RedisDb {

    @Tag("host")
    private String host;
    @Tag("port")
    private Integer port;
    @Tag("password")
    private String password;
    @Tag("dbName")
    private String dbName;


    public RedisDb() {
    }

    public RedisDb(String host, Integer port, String password, String dbName) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.dbName = dbName;
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

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;

    }

    @Override
    public String toString() {
        return "RedisDb{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                ", dbName='" + dbName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RedisDb)) return false;
        RedisDb redisDb = (RedisDb) o;
        return Objects.equals(host, redisDb.host) &&
                Objects.equals(port, redisDb.port) &&
                Objects.equals(password, redisDb.password) &&
                Objects.equals(dbName, redisDb.dbName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, password, dbName);
    }
}
