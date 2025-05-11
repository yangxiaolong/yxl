package com.lego.yxl.splitter.starter;

import com.lego.yxl.splitter.core.ParamSplitter;
import com.lego.yxl.splitter.core.SmartParamSplitter;
import com.lego.yxl.splitter.core.annotation.Split;
import com.lego.yxl.splitter.core.support.executor.ParallelMethodExecutor;
import com.lego.yxl.splitter.core.support.merger.IntResultMerger;
import com.lego.yxl.splitter.core.support.merger.ListResultMerger;
import com.lego.yxl.splitter.core.support.merger.LongResultMerger;
import com.lego.yxl.splitter.core.support.merger.SetResultMerger;
import com.lego.yxl.splitter.core.support.spliter.AnnBasedParamSplitterBuilder;
import com.lego.yxl.splitter.core.support.spliter.ListParamSplitter;
import com.lego.yxl.splitter.core.support.spliter.SetParamSplitter;
import com.lego.yxl.splitter.core.support.spliter.SplittableParamSplitter;
import com.lego.yxl.splitter.core.support.spring.SplitInterceptor;
import com.lego.yxl.splitter.core.support.spring.SplitInvokerProcessor;
import com.lego.yxl.splitter.core.support.spring.invoker.SplitInvokerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

/**
 * AutoConfiguration 自动配置主要完成：<br />
 * 1. PointcutAdvisor, 拦截器配置
 * <p>
 * 2. BeanProcessor，处理器配置，Spring 启动时，对 @Split 注解进行处理
 * <p>
 * 3. SplitInvokerRegistry，InvokerRegistry 使用单例特性，共享注册信息
 * <p>
 * 4. ParallelMethodExecutor，多线程执行器
 * <p>
 * 5. ParamSplitter，预制参数拆分器
 * <p>
 * 6. ResultMerger，预制结果合并器
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
@Role(ROLE_INFRASTRUCTURE)
public class SplitterAutoConfiguration {

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public SplitInvokerRegistry splitInvokerRegistry() {
        return new SplitInvokerRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    @Role(ROLE_INFRASTRUCTURE)
    public SplitInterceptor splitInterceptor(SplitInvokerRegistry splitInvokerRegistry) {
        return new SplitInterceptor(splitInvokerRegistry);
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public PointcutAdvisor pointcutAdvisor(SplitInterceptor splitInterceptor) {
        Pointcut pointcut = new AnnotationMatchingPointcut(null, Split.class);
        return new DefaultPointcutAdvisor(pointcut, splitInterceptor);
    }

    @Bean
    public SplitInvokerProcessor splitInvokerProcessor(SplitInvokerRegistry splitInvokerRegistry) {
        return new SplitInvokerProcessor(splitInvokerRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    @Role(ROLE_INFRASTRUCTURE)
    public ParallelMethodExecutor defaultSplitExecutor(ExecutorService defaultExecutor) {
        return new ParallelMethodExecutor(defaultExecutor, 2);
    }

    @Bean
    @ConditionalOnMissingBean
    @Role(ROLE_INFRASTRUCTURE)
    public ExecutorService defaultExecutor() {
        int cpu = Runtime.getRuntime().availableProcessors();
        int nThreads = cpu * 100;
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("Default-Split-Executor-Thread-%d")
                .daemon(true)
                .uncaughtExceptionHandler((thread, throwable) -> log.error("failed to run task on thread {}", thread, throwable))
                .build();

        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                basicThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public ListResultMerger listResultMerger() {
        return new ListResultMerger();
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public LongResultMerger longResultMerger() {
        return new LongResultMerger();
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public IntResultMerger intResultMerger() {
        return new IntResultMerger();
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public SetResultMerger setResultMerger() {
        return new SetResultMerger();
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public ParamSplitter listParamSplitter() {
        return new ListParamSplitter();
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public ParamSplitter setParamSplitter() {
        return new SetParamSplitter();
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public AnnBasedParamSplitterBuilder annBasedParamSplitterBuilder(List<SmartParamSplitter> smartParamSplitters) {
        return new AnnBasedParamSplitterBuilder(smartParamSplitters);
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public SplittableParamSplitter splittableParamSplitter() {
        return new SplittableParamSplitter();
    }

}
