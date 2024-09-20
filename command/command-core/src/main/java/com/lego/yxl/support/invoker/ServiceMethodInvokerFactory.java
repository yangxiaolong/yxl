package com.lego.yxl.support.invoker;

import java.lang.reflect.Method;

public interface ServiceMethodInvokerFactory {
    ServiceMethodInvoker createForMethod(Method method);
}
