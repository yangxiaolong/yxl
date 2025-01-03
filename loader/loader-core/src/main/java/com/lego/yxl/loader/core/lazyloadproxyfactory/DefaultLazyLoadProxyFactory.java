package com.lego.yxl.loader.core.lazyloadproxyfactory;

import com.lego.yxl.loader.core.lazyloaderinterceptor.LazyLoaderInterceptorFactory;
import org.springframework.aop.framework.ProxyFactory;

public class DefaultLazyLoadProxyFactory extends AbstractLazyLoadProxyFactory implements LazyLoadProxyFactory {

    private final LazyLoaderInterceptorFactory lazyLoaderInterceptorFactory;

    public DefaultLazyLoadProxyFactory(LazyLoaderInterceptorFactory lazyLoaderInterceptorFactory) {
        this.lazyLoaderInterceptorFactory = lazyLoaderInterceptorFactory;
    }

    @Override
    protected <T> T createProxyFor(Class cls, T t) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(t);
        proxyFactory.addAdvice(this.lazyLoaderInterceptorFactory.createFor(cls, t));
        return (T) proxyFactory.getProxy();
    }

}