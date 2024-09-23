package com.lego.yxl;

import com.lego.yxl.annotation.Idempotent;
import com.lego.yxl.core.IdempotentExecutorFactory;
import com.lego.yxl.core.IdempotentMetaParser;
import com.lego.yxl.core.support.*;
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
        SimpleIdempotentExecutorFactory simpleIdempotentExecutorFactory
                = new SimpleIdempotentExecutorFactory();
        simpleIdempotentExecutorFactory.setIdempotentKeyParser(this.idempotentKeyParser);
        simpleIdempotentExecutorFactory.setSerializer(this.serializer);
        simpleIdempotentExecutorFactory.setExecutionRecordRepository(executionRecordRepository);
        return simpleIdempotentExecutorFactory;
    }

    @Bean
    public PointcutAdvisor idempotentPointcutAdvisor(IdempotentInterceptor idempotentInterceptor) {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, Idempotent.class),
                idempotentInterceptor);
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
