package com.lego.yxl.core.command.support.handler.aggfactory;

@FunctionalInterface
public interface AggFactory<CONTEXT, AGG> {
    AGG create(CONTEXT context);
}
