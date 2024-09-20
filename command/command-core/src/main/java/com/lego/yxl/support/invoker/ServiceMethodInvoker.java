package com.lego.yxl.support.invoker;

import java.lang.reflect.Method;

public interface ServiceMethodInvoker {
    Object invoke(Method method, Object[] arguments) ;
}
