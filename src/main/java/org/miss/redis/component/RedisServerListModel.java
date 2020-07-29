package org.miss.redis.component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.component
 * @author: miss
 * @since: 2020/7/29 16:32
 * @history: 1.2020/7/29 created by miss
 */
public class RedisServerListModel extends AbstractListModel<RedisDBComponent> {

    private List<RedisDBComponent> redisServerList = new ArrayList<RedisDBComponent>();

    @Override
    public int getSize() {
        return redisServerList.size();
    }

    @Override
    public RedisDBComponent getElementAt(int index) {
        return redisServerList.get(index);
    }

    public void addElement(RedisDBComponent redisDBComponent) {
        this.redisServerList.add(redisDBComponent);
    }
}
