package org.miss.redis.service;

import com.intellij.openapi.components.ServiceManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public interface RedisApplicationService {
    static RedisApplicationService getInstance() {
        return ServiceManager.getService(RedisApplicationService.class);
    }

    Jedis getJedis();


    JedisShardInfo initJedisSharedInfo(String host, String name, int port, String password);
}