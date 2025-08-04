package com.lego.yxl.core.command.support.handler.aggloader;

abstract class AbstractSmartLoader implements SmartAggLoader {
    protected final Class cmdClass;
    protected final Class aggClass;

    public AbstractSmartLoader(Class cmdClass, Class aggClass) {
        this.cmdClass = cmdClass;
        this.aggClass = aggClass;
    }

    @Override
    public boolean apply(Class cmdClass, Class aggClass) {
        return cmdClass.equals(this.cmdClass) && aggClass.equals(this.aggClass);
    }
}
