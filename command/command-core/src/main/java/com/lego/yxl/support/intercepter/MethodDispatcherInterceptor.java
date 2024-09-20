package com.lego.yxl.support.intercepter;

import com.google.common.collect.Maps;
import com.lego.yxl.support.invoker.ServiceMethodInvoker;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;

public final class MethodDispatcherInterceptor implements MethodInterceptor {
    protected final Map<Method, ServiceMethodInvoker> methodMap = Maps.newHashMap();

    public boolean support(Method method){
        return methodMap.containsKey(method);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method callMethod = invocation.getMethod();
        ServiceMethodInvoker serviceMethodInvoker = this.methodMap.get(callMethod);
        if (serviceMethodInvoker != null){
            return serviceMethodInvoker.invoke(invocation.getMethod(), invocation.getArguments());
        }
        return invocation.proceed();
    }

    public void register(Method callMethod, ServiceMethodInvoker executorMethod){
        this.methodMap.put(callMethod, executorMethod);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        this.methodMap.forEach((method, serviceMethodInvoker) -> {
            stringBuilder
                    .append("\n")
                    .append(method)
                    .append("\n")
                    .append(serviceMethodInvoker);
        });

        return stringBuilder.toString();
    }
}
