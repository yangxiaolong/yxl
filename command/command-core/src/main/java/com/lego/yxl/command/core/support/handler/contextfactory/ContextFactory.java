package com.lego.yxl.command.core.support.handler.contextfactory;

@FunctionalInterface
public interface ContextFactory<CMD, CONTENT> {
    /**
     * 将 CommandObject 转化为 ContextObject
     * @param cmd
     * @return
     */
    CONTENT create(CMD cmd);
}
