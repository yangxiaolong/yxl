package com.lego.yxl.core.command.support.handler.contextfactory;

import com.google.common.base.Preconditions;

public abstract class AbstractSmartContextFactory<CMD, CXT> implements SmartContextFactory<CMD, CXT> {
    protected final Class cmdClass;
    protected final Class contextClass;

    protected AbstractSmartContextFactory(Class cmdClass, Class contextClass) {
        Preconditions.checkArgument(contextClass != null);
        Preconditions.checkArgument(cmdClass != null);
        this.contextClass = contextClass;
        this.cmdClass = cmdClass;
    }

    @Override
    public boolean apply(Class cmdClass, Class contextClass) {
        return this.cmdClass.equals(cmdClass) && this.contextClass.equals(contextClass);
    }
}
