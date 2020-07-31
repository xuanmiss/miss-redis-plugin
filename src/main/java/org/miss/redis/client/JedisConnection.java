package org.miss.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

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

    public static void main(String[] args) {
        JedisShardInfo shardInfo = new JedisShardInfo("k8s.definesys.com", 31006, "xdap_dev_redis_2");
        shardInfo.setPassword("xdapredis");
        shardInfo.setSoTimeout(2000);
        Jedis jedis = shardInfo.createResource();
        ScanParams scanParams = new ScanParams();
        scanParams.count(30);
        scanParams.match("*gate*");
        ScanResult<String> scanResult = jedis.scan("0", scanParams);
        System.out.println(scanResult.getResult());
    }

//    public Jedis initJedis(JedisShardInfo info) {
//        Jedis jedis = new Jedis(info);
//        return jedis;
//    }

//    public Jedis getJedis() {
//        return jedisPool.getResource();
//    }

    public JedisShardInfo initJedisSharedInfo(String host, String name, int port) {
        JedisShardInfo shardInfo = new JedisShardInfo(host, port, name);
        return shardInfo;
    }

//    public JedisPool getJedisPool() {
//        return jedisPool;
//    }

    public JedisShardInfo initJedisSharedInfo(String host, String name, int port, String password) {
        JedisShardInfo shardInfo = this.initJedisSharedInfo(host, name, port);
        shardInfo.setPassword(password);
        shardInfo.setSoTimeout(2000);

        return shardInfo;
    }
}
