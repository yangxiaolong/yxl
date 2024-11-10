package com.lego.yxl.command.core.support.handler.contextfactory;

/**
 * 处理 CommandObject 和 ContextObject 相等的情况
 */
public class EqualsSmartContextFactory implements SmartContextFactory {
    @Override
    public Object create(Object o) {
        return o;
    }

    @Override
    public boolean apply(Class cmdClass, Class contextClass) {
        return cmdClass.equals(contextClass);
    }
}
