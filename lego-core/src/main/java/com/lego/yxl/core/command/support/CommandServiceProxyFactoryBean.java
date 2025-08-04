package com.lego.yxl.core.command.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;


public class CommandServiceProxyFactoryBean<B>
        implements FactoryBean<B>{
    private final Class commandService;

    @Autowired
    private CommandServiceProxyFactory commandServiceProxyFactory;


    public CommandServiceProxyFactoryBean(Class commandService) {
        this.commandService = commandService;
    }

    @Override
    public B getObject() throws Exception {
        return this.commandServiceProxyFactory.createCommandApplicationService(this.commandService);
    }

    @Override
    public Class<?> getObjectType() {
        return this.commandService;
    }

}
