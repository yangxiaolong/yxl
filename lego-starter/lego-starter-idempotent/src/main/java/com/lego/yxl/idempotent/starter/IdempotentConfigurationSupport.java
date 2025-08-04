package com.lego.yxl.idempotent.starter;

import com.lego.yxl.core.idempotent.annotation.Idempotent;
import com.lego.yxl.core.idempotent.core.IdempotentExecutorFactory;
import com.lego.yxl.core.idempotent.core.IdempotentMetaParser;
import com.lego.yxl.core.idempotent.core.support.*;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Map;

public abstract class IdempotentConfigurationSupport {

    @Autowired
    private IdempotentKeyParser idempotentKeyParser;
    @Autowired
    private ExecutionResultSerializer serializer;

    protected IdempotentExecutorFactory createExecutorFactory(ExecutionRecordRepository executionRecordRepository) {
        SimpleIdempotentExecutorFactory factory = new SimpleIdempotentExecutorFactory();
        factory.setIdempotentKeyParser(this.idempotentKeyParser);
        factory.setSerializer(this.serializer);
        factory.setExecutionRecordRepository(executionRecordRepository);
        return factory;
    }

    @Bean
    public PointcutAdvisor idempotentPointcutAdvisor(IdempotentInterceptor idempotentInterceptor) {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, Idempotent.class);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, idempotentInterceptor);
        advisor.setOrder(0);
        return advisor;
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor(IdempotentMetaParser parser,
                                                       IdempotentExecutorFactories factories) {
        return new IdempotentInterceptor(parser, factories);
    }

    @Bean
    public IdempotentMetaParser idempotentMetaParser() {
        return new IdempotentAnnParser();
    }

    @Bean
    public IdempotentExecutorFactories idempotentExecutorFactories(
            Map<String, IdempotentExecutorFactory> factoryMap) {
        return new IdempotentExecutorFactories(factoryMap);
    }

    static class IdempotentKeyParserConfiguration {
        @Bean
        public IdempotentKeyParser idempotentKeyParser() {
            return new SimpleIdempotentKeyParser();
        }
    }

    static class ExecutionResultSerializerConfiguration {
        @Bean
        public ExecutionResultSerializer executionResultSerializer() {
            return new SimpleExecutionResultSerializer();
        }

    }

}
