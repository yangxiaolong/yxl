package com.lego.yxl.command.core.support.invoker;

import java.lang.reflect.Method;

public interface ServiceMethodInvoker {
    Object invoke(Method method, Object[] arguments) ;
}
