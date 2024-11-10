package com.lego.yxl.command.core.support.invoker;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

public class TargetBasedServiceMethodInvokerFactory
    implements ServiceMethodInvokerFactory {
    private final Object target;

    public TargetBasedServiceMethodInvokerFactory(Object target) {
        this.target = target;
    }

    @Override
    public ServiceMethodInvoker createForMethod(Method callMethod) {
        Method targetMethod = MethodUtils.getMatchingAccessibleMethod(target.getClass(), callMethod.getName(), callMethod.getParameterTypes());
        if (targetMethod != null){
            return new TargetBasedServiceMethodInvoker(target, targetMethod);
        }
        return null;
    }
}
