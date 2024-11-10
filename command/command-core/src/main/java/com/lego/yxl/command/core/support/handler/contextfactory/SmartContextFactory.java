package com.lego.yxl.command.core.support.handler.contextfactory;

public interface SmartContextFactory<CMD, CONTEXT> extends ContextFactory<CMD, CONTEXT> {
    /**
     * 当前组件是否支持当前命令和上下文
     * @param cmdClass
     *  CommandObject 类
     * @param contextClass
     * ContextObject 类
     * @return
     */
    boolean apply(Class cmdClass, Class contextClass);
}
