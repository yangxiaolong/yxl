package com.lego.yxl.async.starter;

import com.lego.yxl.core.async.annotation.AsyncForOrderedBasedRocketMQ;
import com.lego.yxl.core.async.order.OrderedAsyncConsumerContainerRegistry;
import com.lego.yxl.core.async.order.OrderedAsyncInterceptor;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
@Role(ROLE_INFRASTRUCTURE)
public class OrderedAsyncAutoConfiguration {

    @Autowired
    private Environment environment;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public OrderedAsyncInterceptor orderedAsyncInterceptor() {
        return new OrderedAsyncInterceptor(this.environment, this.rocketMQTemplate);
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public OrderedAsyncConsumerContainerRegistry orderedAsyncConsumerContainerRegistry() {
        return new OrderedAsyncConsumerContainerRegistry(this.environment);
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public PointcutAdvisor orderedAsyncPointcutAdvisor(OrderedAsyncInterceptor orderedAsyncInterceptor) {
        Pointcut pointcut = new AnnotationMatchingPointcut(null, AsyncForOrderedBasedRocketMQ.class);
        return new DefaultPointcutAdvisor(pointcut, orderedAsyncInterceptor);
    }

}
