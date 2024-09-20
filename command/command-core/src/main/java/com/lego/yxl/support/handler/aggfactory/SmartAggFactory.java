package com.lego.yxl.support.handler.aggfactory;

public interface SmartAggFactory<CONTEXT, AGG>
        extends AggFactory<CONTEXT, AGG> {
    boolean apply(Class<CONTEXT> contextClass, Class<AGG> aggClass);
}
