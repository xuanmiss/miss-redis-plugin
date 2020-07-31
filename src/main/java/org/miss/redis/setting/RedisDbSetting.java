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
public class RedisDbSetting implements PersistentStateComponent<RedisDb> {

//    private static final Logger logger = LoggerFactory.getLogger(RedisDbSetting.class);


    private List<RedisDb> globalRedisDbList = new ArrayList<>(10);

    public static RedisDbSetting getInstance() {
//        logger.info("getInstance start");
        return ServiceManager.getService(RedisDbSetting.class);
    }


    @Nullable
    @Override
    public RedisDb getState() {
        if (globalRedisDbList != null && globalRedisDbList.size() > 0) {
            RedisDb state = new RedisDb();
            XmlSerializerUtil.copyBean(this.globalRedisDbList.get(0), state);
            return state;
        } else {
            return null;
        }
    }

    @Override
    public void loadState(@NotNull RedisDb state) {

        if (this.isRedisDbExit(state)) {
            return;

        } else {
            RedisDb redisDb = new RedisDb();
            XmlSerializerUtil.copyBean(state, redisDb);
            globalRedisDbList.add(redisDb);
//            logger.info("loadState redisDb={}", state);
        }
    }

    public List<RedisDb> getAllRedisDb() {
        return globalRedisDbList;
    }

    public boolean isRedisDbExit(RedisDb state) {

//        logger.info("isRedisDbExit globalRedisDbList={} redis={}", globalRedisDbList, state);
        for (RedisDb redisDb : globalRedisDbList) {
            if (redisDb.equals(state)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "RedisDbSettion{" +
                "globalRedisDbList=" + globalRedisDbList +
                '}';
    }
}
