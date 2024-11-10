package com.lego.yxl.loader.core.lazyloadproxyfactory;

import org.springframework.context.ApplicationContext;

public class AutowiredLazyLoadProxyFactoryWrapper implements LazyLoadProxyFactory {

    private final LazyLoadProxyFactory lazyLoadProxyFactory;
    private final ApplicationContext applicationContext;

    public AutowiredLazyLoadProxyFactoryWrapper(LazyLoadProxyFactory lazyLoadProxyFactory,
                                                ApplicationContext applicationContext) {
        this.lazyLoadProxyFactory = lazyLoadProxyFactory;
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T createProxyFor(T t) {
        if (t != null) {
            applicationContext.getAutowireCapableBeanFactory().autowireBean(t);
        }
        return lazyLoadProxyFactory.createProxyFor(t);
    }

}