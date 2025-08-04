package com.lego.yxl.core.command.support.handler.aggloader;

public interface SmartAggLoader<CMD, AGG> extends AggLoader<CMD, AGG> {
    boolean apply(Class cmdClass, Class aggClass);
}
