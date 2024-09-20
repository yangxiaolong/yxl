package com.lego.yxl.support.invoker;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class TargetBasedServiceMethodInvoker
        implements ServiceMethodInvoker {
    private final Object target;
    private final Method targetMethod;

    public TargetBasedServiceMethodInvoker(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
    }

    @Override
    public Object invoke(Method method, Object[] arguments) {
        return ReflectionUtils.invokeMethod(targetMethod, this.target, arguments);
    }
}
