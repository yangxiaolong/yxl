package com.lego.yxl.loader.core.lazyloaderinterceptor;

import com.google.common.collect.Maps;
import com.lego.yxl.loader.core.propertylazyloader.PropertyLazyLoader;
import com.lego.yxl.loader.core.propertylazyloader.PropertyLazyLoaderFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class LazyLoaderInterceptorFactory {

    private final Map<Class<?>, Map<String, PropertyLazyLoader>> loaderCache = Maps.newConcurrentMap();
    private final PropertyLazyLoaderFactory propertyLazyLoaderFactory;

    public LazyLoaderInterceptorFactory(PropertyLazyLoaderFactory propertyLazyLoaderFactory) {
        this.propertyLazyLoaderFactory = propertyLazyLoaderFactory;
    }

    public LazyLoaderInterceptor createFor(Class<?> cls, Object target) {
        Map<String, PropertyLazyLoader> loaders = this.loaderCache.computeIfAbsent(cls, this::createForClass);

        return new LazyLoaderInterceptor(loaders, target);
    }

    private Map<String, PropertyLazyLoader> createForClass(Class<?> targetCls) {
        List<PropertyLazyLoader> propertyLazyLoaders = propertyLazyLoaderFactory.createFor(targetCls);
        if (CollectionUtils.isEmpty(propertyLazyLoaders)) {
            return Collections.emptyMap();
        }
        return propertyLazyLoaders.stream()
                .filter(Objects::nonNull)
                .collect(toMap(loader -> loader.getField().getName(), Function.identity()));
    }

}
