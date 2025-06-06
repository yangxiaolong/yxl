package com.lego.yxl.async.core.order;

import com.lego.yxl.async.core.annotation.AsyncForOrderedBasedRocketMQ;
import com.lego.yxl.support.AbstractConsumerContainerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.env.Environment;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class OrderedAsyncConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public OrderedAsyncConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @Nonnull
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object proxy, @Nonnull String beanName) throws BeansException {
        // 1. 获取 @AsyncForOrderedBasedRocketMQ 注解方法
        Class<?> targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, AsyncForOrderedBasedRocketMQ.class);

        Object bean = AopProxyUtils.getSingletonTarget(proxy);

        // 2. 为每个 @AsyncForOrderedBasedRocketMQ 注解方法 注册 OrderedAsyncConsumerContainer
        for (Method method : methodsListWithAnnotation) {
            if (method.isBridge()) {
                log.warn("method {} is bridge, break!", method);
                continue;
            }

            AsyncForOrderedBasedRocketMQ annotation = method.getAnnotation(AsyncForOrderedBasedRocketMQ.class);
            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)) {
                continue;
            }

            OrderedAsyncConsumerContainer consumerContainer = new OrderedAsyncConsumerContainer(
                    this.getEnvironment(),
                    annotation,
                    bean,
                    method
            );
            consumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(consumerContainer);
        }
        return proxy;
    }
}
