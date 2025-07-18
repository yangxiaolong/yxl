package com.lego.yxl.loader.core.lazyloaderinterceptor;

import com.lego.yxl.loader.core.propertylazyloader.PropertyLazyLoader;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Map;

import static ch.qos.logback.core.joran.util.beans.BeanUtil.getPropertyName;
import static ch.qos.logback.core.joran.util.beans.BeanUtil.isGetter;

public class LazyLoaderInterceptor implements InvocationHandler, MethodInterceptor {

    private final Map<String, PropertyLazyLoader> lazyLoaderCache;
    private final Object target;

    public LazyLoaderInterceptor(Map<String, PropertyLazyLoader> lazyLoaderCache, Object target) {
        this.target = target;
        this.lazyLoaderCache = lazyLoaderCache;
    }

    @Override
    public Object invoke(@javax.annotation.Nonnull MethodInvocation methodInvocation) throws Throwable {
        Object proxy;
        if (methodInvocation instanceof ProxyMethodInvocation proxyMethodInvocation) {
            proxy = proxyMethodInvocation.getProxy();
        } else {
            proxy = methodInvocation.getThis();
        }
        return invoke(proxy, methodInvocation.getMethod(), methodInvocation.getArguments());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (isGetter(method)) {
            String propertyName = getPropertyName(method);
            PropertyLazyLoader propertyLazyLoader = this.lazyLoaderCache.get(propertyName);

            if (propertyLazyLoader != null) {
                Object data = method.invoke(target, objects);
                if (data != null) {
                    return data;
                }

                data = propertyLazyLoader.loadData(o);

                if (data != null) {
                    FieldUtils.writeField(target, propertyName, data, true);
                }
                return data;
            }
        }

        return method.invoke(target, objects);
    }

}
