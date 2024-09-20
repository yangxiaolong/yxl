package com.lego.yxl.support.method;

import com.google.common.base.Preconditions;
import com.lego.yxl.support.handler.CommandHandler;
import com.lego.yxl.support.invoker.ServiceMethodInvoker;

import java.lang.reflect.Method;

public class CommandHandlerBasedServiceMethodInvoker implements ServiceMethodInvoker {
    private final CommandHandler commandHandler;

    public CommandHandlerBasedServiceMethodInvoker(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public Object invoke(Method method, Object[] arguments) {
        Preconditions.checkArgument(arguments.length == 1);
        Object param = arguments[0];
        return commandHandler.handle(param);
    }

    @Override
    public String toString(){
        return this.commandHandler.toString();
    }
}
