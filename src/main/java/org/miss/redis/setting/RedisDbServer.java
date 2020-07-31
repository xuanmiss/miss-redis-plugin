package org.miss.redis.setting;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.setting
 * @author: miss
 * @since: 2020/7/31 15:08
 * @history: 1.2020/7/31 created by miss
 */
public class RedisDbServer {
    private List<RedisDb> redisDbList = new ArrayList<>(10);

    public List<RedisDb> getRedisDbList() {
        return redisDbList;
    }

    public void setRedisDbList(List<RedisDb> redisDbList) {
        this.redisDbList = redisDbList;
    }

    public RedisDbServer() {
    }

    public RedisDbServer(List<RedisDb> redisDbList) {
        this.redisDbList = redisDbList;
    }

}
