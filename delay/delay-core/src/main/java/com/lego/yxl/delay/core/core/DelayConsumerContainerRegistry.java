package com.lego.yxl.delay.core.core;

import com.lego.yxl.delay.core.annotation.DelayBasedRocketMQ;
import com.lego.yxl.support.AbstractConsumerContainerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DelayConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public DelayConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @Nonnull
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object proxy, @Nonnull String beanName) throws BeansException {
        // 1. 获取 @DelayBasedRocketMQ 注解方法
        Class<?> targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, DelayBasedRocketMQ.class);

        Object bean = AopProxyUtils.getSingletonTarget(proxy);

        // 2. 为每个 @DelayBasedRocketMQ 注解方法 注册 RocketMQConsumerContainer
        for (Method method : methodsListWithAnnotation) {
            if (method.isBridge()) {
                log.warn("method {} is bridge, break!", method);
                continue;
            }
            DelayBasedRocketMQ annotation = AnnotatedElementUtils.findMergedAnnotation(method, DelayBasedRocketMQ.class);
            if (Objects.isNull(annotation)) {
                log.warn("method annotation DelayBasedRocketMQ {} is null, break!", method);
                continue;
            }
            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)) {
                continue;
            }

            DelayConsumerContainer delayConsumerContainer =
                    new DelayConsumerContainer(this.getEnvironment(), annotation, bean, method);
            delayConsumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(delayConsumerContainer);
        }
        return proxy;
    }

}
