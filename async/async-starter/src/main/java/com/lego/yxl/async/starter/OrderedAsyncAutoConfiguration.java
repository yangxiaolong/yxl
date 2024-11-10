package com.lego.yxl.async.starter;

import com.lego.yxl.async.core.annotation.AsyncForOrderedBasedRocketMQ;
import com.lego.yxl.async.core.order.OrderedAsyncConsumerContainerRegistry;
import com.lego.yxl.async.core.order.OrderedAsyncInterceptor;
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
public class OrderedAsyncAutoConfiguration {
    @Autowired
    private Environment environment;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();


    @Bean
    public OrderedAsyncInterceptor orderedAsyncInterceptor(){
        return new OrderedAsyncInterceptor(this.environment, this.rocketMQTemplate, parameterNameDiscoverer);
    }

    @Bean
    public OrderedAsyncConsumerContainerRegistry orderedAsyncConsumerContainerRegistry(){
        return new OrderedAsyncConsumerContainerRegistry(this.environment);
    }
    @Bean
    public PointcutAdvisor orderedAsyncPointcutAdvisor(@Autowired OrderedAsyncInterceptor sendMessageInterceptor){
        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, AsyncForOrderedBasedRocketMQ.class),
                sendMessageInterceptor);
    }
}
