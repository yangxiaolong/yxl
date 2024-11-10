package com.lego.yxl.command.core.support.invoker;

import java.lang.reflect.Method;

public interface ServiceMethodInvokerFactory {
    ServiceMethodInvoker createForMethod(Method method);
}
