package com.lego.yxl.command.core.support.handler.preaction;

public interface SmartPreOperation<CONTEXT> extends PreOperation<CONTEXT>{

    boolean support(Class cls);
}
