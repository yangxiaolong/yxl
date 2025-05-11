package com.lego.yxl.delay.starter;

import com.lego.yxl.delay.core.annotation.DelayBasedRocketMQ;
import com.lego.yxl.delay.core.core.DelayConsumerContainerRegistry;
import com.lego.yxl.delay.core.core.DelayMethodInterceptor;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DelayBasedRocketMQAutoConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DelayMethodInterceptor delayInterceptor() {
        return new DelayMethodInterceptor(this.environment, this.rocketMQTemplate);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DelayConsumerContainerRegistry delayConsumerContainerRegistry() {
        return new DelayConsumerContainerRegistry(this.environment);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor delayPointcutAdvisor(DelayMethodInterceptor delayMethodInterceptor) {
        Pointcut pointcut = new AnnotationMatchingPointcut(null, DelayBasedRocketMQ.class);
        return new DefaultPointcutAdvisor(pointcut, delayMethodInterceptor);
    }
}
