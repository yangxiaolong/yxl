package com.lego.yxl.command.core.support.invoker;

import com.lego.yxl.command.core.Command;
import com.lego.yxl.command.core.CommandService;

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
