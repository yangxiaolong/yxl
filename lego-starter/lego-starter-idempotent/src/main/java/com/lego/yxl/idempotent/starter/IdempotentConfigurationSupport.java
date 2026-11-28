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
        /*
        IdempotentInterceptor` 会给每个被注解的方法缓存一个 `IdempotentExecutor`，
        而且幂等切面的 order 是 0，优先级高于事务切面。也就是说，**幂等拦截在事务外层**。
        这样业务方法抛异常、事务回滚之后，执行记录仍然能被正常更新为“失败”。
        如果顺序反了，失败记录可能跟着事务一起回滚，重试时就查不到档案了。
         */
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
