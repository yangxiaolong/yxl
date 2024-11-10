package com.lego.yxl.command.core.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class CommandApplicationServiceProxyFactoryBean<B>
        implements FactoryBean<B>{
    private final Class commandService;

    @Autowired
    private CommandApplicationServiceProxyFactory commandApplicationServiceProxyFactory;


    public CommandApplicationServiceProxyFactoryBean(Class commandService) {
        this.commandService = commandService;
    }

    @Override
    public B getObject() throws Exception {
        return this.commandApplicationServiceProxyFactory.createCommandApplicationService(this.commandService);
    }

    @Override
    public Class<?> getObjectType() {
        return this.commandService;
    }

}
