package com.lego.yxl.core.command.support.handler.preaction;

public interface SmartPreOperation<CONTEXT> extends PreOperation<CONTEXT>{

    boolean support(Class cls);
}
