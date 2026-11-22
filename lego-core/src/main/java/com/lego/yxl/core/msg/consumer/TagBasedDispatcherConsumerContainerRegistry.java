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
        // (1)拿到代理背后的真实类型。如果这个 Bean 被 AOP 代理了，直接在代理类上扫注解是扫不到的；
        Class<?> targetCls = AopUtils.getTargetClass(proxy);

        // 获取 @TagBasedDispatcherMessageConsumer 注解方法
        // (2)findMergedAnnotation`：支持组合注解，注解被元注解包装过也能找到；
        TagBasedDispatcherMessageConsumer consumer = AnnotatedElementUtils
                .findMergedAnnotation(targetCls, TagBasedDispatcherMessageConsumer.class);

        // (3)判断注解存在才建容器，没注解的 Bean 原样返回，不做任何多余动作。
        if (consumer != null) {
            // 为每个 @TagBasedDispatcherMessageConsumer 注册 TagBasedDispatcherConsumerContainer
            TagBasedDispatcherConsumerContainer consumerContainer =
                    new TagBasedDispatcherConsumerContainer(this.getEnvironment(), proxy, consumer);
            consumerContainer.afterPropertiesSet();

            this.getConsumerContainers().add(consumerContainer);
        }

        return proxy;
    }

}
