package org.miss.redis.setting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.setting
 * @author: miss
 * @since: 2020/7/27 16:44
 * @history: 1.2020/7/27 created by miss
 */
@State(
        name = "redisDBSetting",
        storages = @Storage("redis-manager-db.xml"),
        defaultStateAsResource = true,
        externalStorageOnly = true
)
public class RedisDbSetting implements PersistentStateComponent<RedisDbServer> {


    private RedisDbServer redisDbServer = new RedisDbServer();


    public static RedisDbSetting getInstance() {
//        logger.info("getInstance start");
        return ServiceManager.getService(RedisDbSetting.class);
    }


    public boolean isRedisDbExit(RedisDb state) {

//        logger.info("isRedisDbExit globalRedisDbList={} redis={}", globalRedisDbList, state);
        for (RedisDb redisDb : redisDbServer.getRedisDbList()) {
            if (redisDb.equals(state)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "RedisDbSetting{" +
                "redisDbServer=" + redisDbServer +
                '}';
    }

    @Nullable
    @Override
    public RedisDbServer getState() {
        if (redisDbServer != null && redisDbServer.getRedisDbList().size() > 0) {
            RedisDbServer state = new RedisDbServer();
            XmlSerializerUtil.copyBean(this.redisDbServer, state);
            return state;
        } else {
            return null;
        }
    }

    @Override
    public void loadState(@NotNull RedisDbServer state) {
        RedisDbServer redisDbServer = new RedisDbServer();
        XmlSerializerUtil.copyBean(state, redisDbServer);
        this.redisDbServer = redisDbServer;
    }


    public void addRedisDb(RedisDb redisDb) {
        this.redisDbServer.getRedisDbList().add(redisDb);
        this.loadState(this.redisDbServer);
    }

    public List<RedisDb> getAllDbList() {
        return this.redisDbServer.getRedisDbList();
    }
}
