package org.miss.redis.service.impl;

import org.miss.redis.component.RedisDBComponent;

import javax.swing.*;
import java.awt.*;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.service.impl
 * @author: miss
 * @since: 2020/7/29 16:41
 * @history: 1.2020/7/29 created by miss
 */
public class RedisServerRender extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof RedisDBComponent) {
            RedisDBComponent redisDBComponent = (RedisDBComponent) value;
            try {
                setText(redisDBComponent.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

}
