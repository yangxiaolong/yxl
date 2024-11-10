package com.lego.yxl.msg.core.consumer;

import com.lego.yxl.msg.core.annotation.TagBasedDispatcherMessageConsumer;
import com.lego.yxl.support.AbstractConsumerContainerRegistry;
import lombok.SneakyThrows;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;

public class TagBasedDispatcherConsumerContainerRegistry extends AbstractConsumerContainerRegistry {

    public TagBasedDispatcherConsumerContainerRegistry(Environment environment) {
        super(environment);
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object proxy, String beanName) throws BeansException {
        // 1. 获取 @TagBasedDispatcherMessageConsumer 注解方法
        Class targetCls = AopUtils.getTargetClass(proxy);

        TagBasedDispatcherMessageConsumer tagBasedDispatcherMessageConsumer
                = AnnotatedElementUtils.findMergedAnnotation(targetCls, TagBasedDispatcherMessageConsumer.class);
        if (tagBasedDispatcherMessageConsumer != null) {
            // 2. 为每个 @TagBasedDispatcherMessageConsumer 注册 TagBasedDispatcherConsumerContainer
//            Object bean = AopProxyUtils.getSingletonTarget(proxy);
            TagBasedDispatcherConsumerContainer consumerContainer =
                    new TagBasedDispatcherConsumerContainer(this.getEnvironment(),
                            proxy,
                            tagBasedDispatcherMessageConsumer);
            consumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(consumerContainer);
        }

        return proxy;
    }

}
