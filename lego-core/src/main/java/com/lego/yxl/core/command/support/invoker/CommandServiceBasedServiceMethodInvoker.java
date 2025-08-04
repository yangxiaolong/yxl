package com.lego.yxl.core.command.support.invoker;

import com.lego.yxl.core.command.Command;
import com.lego.yxl.core.command.CommandService;

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
