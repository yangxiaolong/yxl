package com.lego.yxl.support.handler.aggfactory;

import com.google.common.base.Preconditions;

public abstract class AbstractSmartAggFactory<CXT, AGG> implements SmartAggFactory<CXT, AGG> {

    protected final Class contextClass;
    protected final Class aggClass;

    public AbstractSmartAggFactory(Class contextClass, Class aggClass) {
        Preconditions.checkArgument(contextClass != null);
        Preconditions.checkArgument(aggClass != null);
        this.contextClass = contextClass;
        this.aggClass = aggClass;
    }

    @Override
    public boolean apply(Class contextClass, Class aggClass) {
        return this.contextClass.equals(contextClass) && this.aggClass.equals(aggClass);
    }

}
