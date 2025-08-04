package com.lego.yxl.core.command.support.handler.converter;


import com.google.common.base.Preconditions;

public abstract class AbstractSmartResultConverter<AGG, CXT, RESULT> implements SmartResultConverter<AGG, CXT, RESULT>{
    private final Class aggClass;
    private final Class cxtClass;
    private final Class resultClass;

    protected AbstractSmartResultConverter(Class aggClass, Class cxtClass, Class resultClass) {
        Preconditions.checkArgument(aggClass != null);
        Preconditions.checkArgument(cxtClass != null);
        Preconditions.checkArgument(resultClass != null);

        this.aggClass = aggClass;
        this.cxtClass = cxtClass;
        this.resultClass = resultClass;
    }

    @Override
    public boolean apply(Class aggClass, Class contextClass, Class resultClass) {
        return aggClass.equals(this.aggClass)
                && contextClass.equals(this.cxtClass)
                && resultClass.equals(this.resultClass);
    }
}
