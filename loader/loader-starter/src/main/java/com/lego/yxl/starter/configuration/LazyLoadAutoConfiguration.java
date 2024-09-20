package com.lego.yxl.starter.configuration;

import com.lego.yxl.lazyloadproxyfactory.LazyLoadProxyFactory;
import com.lego.yxl.lazyloadproxyfactory.AutowiredLazyLoadProxyFactoryWrapper;
import com.lego.yxl.lazyloadproxyfactory.DefaultLazyLoadProxyFactory;
import com.lego.yxl.lazyloaderinterceptor.LazyLoaderInterceptorFactory;
import com.lego.yxl.propertylazyloader.PropertyLazyLoaderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 */
@Configuration
public class LazyLoadAutoConfiguration {

    @Bean
    public LazyLoadProxyFactory lazyLoadProxyFactory(LazyLoaderInterceptorFactory lazyLoaderInterceptorFactory,
                                                     ApplicationContext applicationContext){
        LazyLoadProxyFactory lazyLoadProxyFactory = new DefaultLazyLoadProxyFactory(lazyLoaderInterceptorFactory);
        return new AutowiredLazyLoadProxyFactoryWrapper(lazyLoadProxyFactory, applicationContext);
    }

    @Bean
    public LazyLoaderInterceptorFactory lazyLoaderInterceptorFactory(PropertyLazyLoaderFactory lazyLoaderFactory){
        return new LazyLoaderInterceptorFactory(lazyLoaderFactory);
    }

    @Bean
    public PropertyLazyLoaderFactory propertyLazyLoaderFactory(ApplicationContext applicationContext){
        return new PropertyLazyLoaderFactory(applicationContext);
    }
}
