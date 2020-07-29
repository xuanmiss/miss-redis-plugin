package org.miss.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.client
 * @author: miss
 * @since: 2020/7/27 18:26
 * @history: 1.2020/7/27 created by miss
 */
public class JedisConnection {

//    private static JedisPool jedisPool;

//    static {
//        JedisPoolConfig config = new JedisPoolConfig();
//        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 10000, null);
//    }

    public JedisShardInfo initJedisSharedInfo(String host, String name, int port) {
        JedisShardInfo shardInfo = new JedisShardInfo(host, port, name);
        return shardInfo;
    }

//    public Jedis initJedis(JedisShardInfo info) {
//        Jedis jedis = new Jedis(info);
//        return jedis;
//    }

//    public Jedis getJedis() {
//        return jedisPool.getResource();
//    }

    public JedisShardInfo initJedisSharedInfo(String host, String name, int port, String password) {
        JedisShardInfo shardInfo = this.initJedisSharedInfo(host, name, port);
        shardInfo.setPassword(password);
        shardInfo.setSoTimeout(2000);

        return shardInfo;
    }

//    public JedisPool getJedisPool() {
//        return jedisPool;
//    }
}
