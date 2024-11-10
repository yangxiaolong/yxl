package com.lego.yxl.command.core.support.handler.aggfactory;

@FunctionalInterface
public interface AggFactory<CONTEXT, AGG> {
    AGG create(CONTEXT context);
}
