package com.lego.yxl.core.async.normal;

import com.lego.yxl.core.async.annotation.AsyncBasedRocketMQ;
import com.lego.yxl.core.singlequery.support.AbstractConsumerContainerRegistry;
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

/**
 * 基于 BeanPostProcessor#postProcessAfterInitialization 对每个 bean 进行处理
 * 扫描 bean 中被 @AsyncBasedRocketMQ 注解的方法，并将方法封装成 AsyncConsumerContainer，
 * 以启动 DefaultMQPushConsumer
 */
@Slf4j
public class NormalAsyncConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public NormalAsyncConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    /**
     * 对每个 bean 依次进行处理
     *
     * @param proxy
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Nonnull
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object proxy, @Nonnull String beanName) throws BeansException {
        // 1. 获取 @AsyncBasedRocketMQ 注解方法
        Class<?> targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, AsyncBasedRocketMQ.class);

        Object bean = AopProxyUtils.getSingletonTarget(proxy);

        // 2. 为每个 @AsyncBasedRocketMQ 注解方法 注册 NormalAsyncConsumerContainer
        for (Method method : methodsListWithAnnotation) {
            if (method.isBridge()) {
                log.warn("method {} is bridge, break!", method);
                continue;
            }

            AsyncBasedRocketMQ annotation = method.getAnnotation(AsyncBasedRocketMQ.class);
            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)) {
                continue;
            }

            NormalAsyncConsumerContainer asyncConsumerContainer =
                    new NormalAsyncConsumerContainer(this.getEnvironment(), annotation, bean, method);
            asyncConsumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(asyncConsumerContainer);
        }

        return proxy;
    }
}
