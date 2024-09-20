package com.lego.yxl.support.handler.preaction;

public interface SmartPreOperation<CONTEXT> extends PreOperation<CONTEXT>{

    boolean support(Class cls);
}
