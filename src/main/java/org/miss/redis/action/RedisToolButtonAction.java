package org.miss.redis.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.miss.redis.RedisConnectionPage;

public class RedisToolButtonAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        RedisConnectionPage form = new RedisConnectionPage();
    }
}
