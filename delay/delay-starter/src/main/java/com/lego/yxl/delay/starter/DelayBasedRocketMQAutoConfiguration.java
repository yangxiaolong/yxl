package com.lego.yxl.delay.starter;

import com.lego.yxl.delay.core.annotation.DelayBasedRocketMQ;
import com.lego.yxl.delay.core.core.DelayConsumerContainerRegistry;
import com.lego.yxl.delay.core.core.DelayMethodInterceptor;
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
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.env.Environment;


@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
public class DelayBasedRocketMQAutoConfiguration {
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Autowired
    private Environment environment;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    public DelayMethodInterceptor delayInterceptor() {
        return new DelayMethodInterceptor(this.environment, this.rocketMQTemplate, parameterNameDiscoverer);
    }

    @Bean
    public DelayConsumerContainerRegistry delayConsumerContainerRegistry() {
        return new DelayConsumerContainerRegistry(this.environment);
    }

    @Bean
    public PointcutAdvisor delayPointcutAdvisor(@Autowired DelayMethodInterceptor delayMethodInterceptor) {
        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, DelayBasedRocketMQ.class),
                delayMethodInterceptor);
    }
}
