package com.lego.yxl.core.msg.consumer;

import com.lego.yxl.core.msg.annotation.TagBasedDispatcherMessageConsumer;
import com.lego.yxl.core.singlequery.support.AbstractConsumerContainerRegistry;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;

public class TagBasedDispatcherConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public TagBasedDispatcherConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @NotNull
    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@NotNull Object proxy, @NotNull String beanName)
            throws BeansException {
        // 1. 获取 @TagBasedDispatcherMessageConsumer 注解方法
        Class<?> targetCls = AopUtils.getTargetClass(proxy);

        TagBasedDispatcherMessageConsumer consumer = AnnotatedElementUtils
                .findMergedAnnotation(targetCls, TagBasedDispatcherMessageConsumer.class);
        if (consumer != null) {
            // 2. 为每个 @TagBasedDispatcherMessageConsumer 注册 TagBasedDispatcherConsumerContainer
            // Object bean = AopProxyUtils.getSingletonTarget(proxy);
            TagBasedDispatcherConsumerContainer consumerContainer =
                    new TagBasedDispatcherConsumerContainer(
                            this.getEnvironment(),
                            proxy,
                            consumer);
            consumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(consumerContainer);
        }

        return proxy;
    }

}
