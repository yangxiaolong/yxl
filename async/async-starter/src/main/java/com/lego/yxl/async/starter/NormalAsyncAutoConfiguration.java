package com.lego.yxl.async.starter;

import com.lego.yxl.async.annotation.AsyncBasedRocketMQ;
import com.lego.yxl.async.normal.NormalAsyncConsumerContainerRegistry;
import com.lego.yxl.async.normal.NormalAsyncInterceptor;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
public class NormalAsyncAutoConfiguration {
    @Autowired
    private Environment environment;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    public NormalAsyncInterceptor asyncInterceptor() {
        return new NormalAsyncInterceptor(this.environment, this.rocketMQTemplate);
    }

    @Bean
    public NormalAsyncConsumerContainerRegistry asyncConsumerContainerRegistry() {
        return new NormalAsyncConsumerContainerRegistry(environment);
    }

    @Bean
    public PointcutAdvisor asyncPointcutAdvisor(@Autowired NormalAsyncInterceptor sendMessageInterceptor) {
        return new DefaultPointcutAdvisor(
                new AnnotationMatchingPointcut(null, AsyncBasedRocketMQ.class),
                sendMessageInterceptor);
    }
}
