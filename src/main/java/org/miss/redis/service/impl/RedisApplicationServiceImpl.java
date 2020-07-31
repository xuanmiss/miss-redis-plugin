package org.miss.redis.service.impl;

import org.miss.redis.client.JedisConnection;
import org.miss.redis.service.RedisApplicationService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.service.impl
 * @author: miss
 * @since: 2020/7/27 18:15
 * @history: 1.2020/7/27 created by miss
 */
public class RedisApplicationServiceImpl implements RedisApplicationService {

    private static JedisConnection jedisConnection;

    static {
        jedisConnection = new JedisConnection();
    }

    private JedisShardInfo jedisShardInfo;

    @Override
    public Jedis getJedis() {
        return jedisShardInfo.createResource();
    }


    @Override
    public JedisShardInfo initJedisSharedInfo(String host, String name, int port, String password) {
        this.jedisShardInfo = jedisConnection.initJedisSharedInfo(host, name, port, password);
        return jedisShardInfo;
    }
}
