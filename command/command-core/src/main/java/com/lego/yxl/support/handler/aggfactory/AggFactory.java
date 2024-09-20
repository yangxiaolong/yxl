package com.lego.yxl.support.handler.aggfactory;

@FunctionalInterface
public interface AggFactory<CONTEXT, AGG> {
    AGG create(CONTEXT context);
}
