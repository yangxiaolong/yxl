package com.lego.yxl.support.invoker;

import com.lego.yxl.Command;
import com.lego.yxl.CommandService;

import java.lang.reflect.Method;

public class CommandServiceBasedServiceMethodInvoker
        implements ServiceMethodInvoker {
    private final CommandService commandService;

    public CommandServiceBasedServiceMethodInvoker(CommandService commandService) {
        this.commandService = commandService;
    }


    @Override
    public Object invoke(Method method, Object[] arguments) {
        return commandService.execute((Command) arguments[0]);
    }
}
