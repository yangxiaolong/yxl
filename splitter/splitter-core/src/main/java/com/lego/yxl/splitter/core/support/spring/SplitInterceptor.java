package com.lego.yxl.splitter.core.support.spring;

import com.lego.yxl.splitter.core.support.spring.invoker.SplitInvoker;
import com.lego.yxl.splitter.core.support.spring.invoker.SplitInvokerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 方法拦截器，将方法调用路由至 拆分逻辑
 */
@Slf4j
public class SplitInterceptor implements MethodInterceptor {

    private final SplitInvokerRegistry splitInvokerRegistry;

    public SplitInterceptor(SplitInvokerRegistry splitInvokerRegistry) {
        this.splitInvokerRegistry = splitInvokerRegistry;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object target = methodInvocation.getThis();
        Object[] params = methodInvocation.getArguments();

        SplitInvoker splitInvoker = this.splitInvokerRegistry.getByMethod(method);
        if (splitInvoker == null) {
            log.warn("failed to find split invoker for method {}", method);
            return method.invoke(target, params);
        } else {
            return splitInvoker.invoke(target, params);
        }
    }

}
