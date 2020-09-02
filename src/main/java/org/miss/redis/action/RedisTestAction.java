package org.miss.redis.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.miss.redis.RedisManager;
import org.miss.redis.RedisTestForm;

public class RedisTestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        RedisTestForm redisTestForm = new RedisTestForm();

    }
}
