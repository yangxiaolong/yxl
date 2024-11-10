package com.lego.yxl.msg.starter;

import com.lego.yxl.msg.core.consumer.TagBasedDispatcherConsumerContainerRegistry;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
public class TabBasedDispatcherMessageConsumerAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public TagBasedDispatcherConsumerContainerRegistry tagBasedDispatcherConsumerContainerRegistry(){
        return new TagBasedDispatcherConsumerContainerRegistry(environment);
    }
}
