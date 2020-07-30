package org.miss.redis.service.impl;

import javax.swing.*;
import java.awt.*;

/**
 * @project: miss-redis-plugin
 * @package: org.miss.redis.service.impl
 * @author: miss
 * @since: 2020/7/30 00:22
 * @history: 1.2020/7/30 created by miss
 */
public class RedisKeysRender extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//        if(value.toString().length() > 10) {
//            setText(value.toString().substring(value.toString().length() - 10));
//        }else {
            setText(value.toString());
//        }

        return this;
    }
}
