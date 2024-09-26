package com.lego.yxl.delay.core;

import com.lego.yxl.delay.annotation.DelayBasedRocketMQ;
import com.lego.yxl.support.AbstractConsumerContainerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class DelayConsumerContainerRegistry
    extends AbstractConsumerContainerRegistry {

    public DelayConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object proxy, String beanName) throws BeansException {
        // 1. 获取 @DelayBasedRocketMQ 注解方法
        Class targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, DelayBasedRocketMQ.class);

        // 2. 为每个 @DelayBasedRocketMQ 注解方法 注册 RocketMQConsumerContainer
        for(Method method : methodsListWithAnnotation){
            if (method.isBridge()){
                log.warn("method {} is bridge, break!", method);
                continue;
            }
            DelayBasedRocketMQ annotation = AnnotatedElementUtils.findMergedAnnotation(method, DelayBasedRocketMQ.class);
            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)){
                continue;
            }

            Object bean = AopProxyUtils.getSingletonTarget(proxy);
            DelayConsumerContainer delayConsumerContainer =
                    new DelayConsumerContainer(this.getEnvironment(),
                            annotation,
                            bean,
                            method);
            delayConsumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(delayConsumerContainer);
        }
        return proxy;
    }
}
