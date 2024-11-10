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

import java.lang.reflect.Method;
import java.util.List;


@Slf4j
public class OrderedAsyncConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public OrderedAsyncConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object proxy, String s) throws BeansException {
        // 1. 获取 @AsyncForOrderedBasedRocketMQ 注解方法
        Class targetCls = AopUtils.getTargetClass(proxy);
        List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(targetCls, AsyncForOrderedBasedRocketMQ.class);

        // 2. 为每个 @AsyncForOrderedBasedRocketMQ 注解方法 注册 OrderedAsyncConsumerContainer
        for(Method method : methodsListWithAnnotation){
            if (method.isBridge()){
                log.warn("method {} is bridge, break!", method);
                continue;
            }

            AsyncForOrderedBasedRocketMQ annotation = method.getAnnotation(AsyncForOrderedBasedRocketMQ.class);
            String consumerProfile = annotation.consumerProfile();
            if (!isActiveProfile(consumerProfile)){
                continue;
            }

            Object bean = AopProxyUtils.getSingletonTarget(proxy);
            OrderedAsyncConsumerContainer asyncConsumerContainer =
                    new OrderedAsyncConsumerContainer(this.getEnvironment(),
                            annotation,
                            bean,
                            method);
            asyncConsumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(asyncConsumerContainer);
        }
        return proxy;
    }
}
