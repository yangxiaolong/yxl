package com.lego.yxl.command.core.support.proxy;

public class DefaultProxyObject implements ProxyObject{
    private final Class itf;

    public DefaultProxyObject(Class itf) {
        this.itf = itf;
    }

    @Override
    public Class getInterface() {
        return this.itf;
    }
}
