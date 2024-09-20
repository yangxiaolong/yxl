package com.lego.yxl.starter.configuration;

import com.lego.yxl.service.DefaultJoinService;
import com.lego.yxl.service.JoinService;
import com.lego.yxl.factory.item.JoinInMemoryBasedJoinItemExecutorFactory;
import com.lego.yxl.factory.item.JoinItemExecutorFactory;
import com.lego.yxl.factory.items.DefaultJoinItemsExecutorFactory;
import com.lego.yxl.factory.items.JoinItemsExecutorFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Configuration
public class JoinInMemoryAutoConfiguration {

    @Bean
    public JoinInMemoryBasedJoinItemExecutorFactory joinInMemoryBasedJoinItemExecutorFactory(
            ApplicationContext applicationContext) {
        return new JoinInMemoryBasedJoinItemExecutorFactory(new BeanFactoryResolver(applicationContext));
    }

    @Bean
    public ExecutorService defaultExecutor() {
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("JoinInMemory-Thread-%d")
                .daemon(true)
                .build();
        int maxSize = Runtime.getRuntime().availableProcessors() * 3;
        return new ThreadPoolExecutor(0, maxSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                basicThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    @ConditionalOnMissingBean
    public JoinItemsExecutorFactory joinItemsExecutorFactory(
            Collection<? extends JoinItemExecutorFactory> joinItemExecutorFactories,
            Map<String, ExecutorService> executorServiceMap) {
        return new DefaultJoinItemsExecutorFactory(joinItemExecutorFactories, executorServiceMap);
    }

    @Bean
    @ConditionalOnMissingBean
    public JoinService joinService(JoinItemsExecutorFactory joinItemsExecutorFactory) {
        return new DefaultJoinService(joinItemsExecutorFactory);
    }

}
