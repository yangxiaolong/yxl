package com.lego.yxl.core.command.support.invoker;

import java.lang.reflect.Method;

public interface ServiceMethodInvokerFactory {
    ServiceMethodInvoker createForMethod(Method method);
}
