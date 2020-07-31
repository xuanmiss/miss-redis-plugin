package org.miss.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.utils
 * @author: miss
 * @since: 2020/7/27 16:28
 * @history: 1.2020/7/27 created by miss
 */
public class RedisUtils {

    private static final String PONG = "PONG";


    public static boolean connectStatus(String host, Integer port, String password, String redisServerName) {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port, redisServerName);
        if (null != password && !password.isEmpty()) {
            jedisShardInfo.setPassword(password);
        }

        try {
            Jedis jedis = new Jedis(jedisShardInfo);
            if (jedis.ping().equals(PONG)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }
}
