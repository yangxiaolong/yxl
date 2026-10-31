package com.lego.yxl.core.splitter.support.spring;

import com.lego.yxl.core.splitter.*;
import com.lego.yxl.core.splitter.annotation.Split;
import com.lego.yxl.core.splitter.annotation.SplitParam;
import com.lego.yxl.core.splitter.support.DefaultSplitService;
import com.lego.yxl.core.splitter.support.ParamSplitterBuilder;
import com.lego.yxl.core.splitter.support.spliter.InvokeParamsSplitter;
import com.lego.yxl.core.splitter.support.spring.invoker.MultipleParamSplitInvoker;
import com.lego.yxl.core.splitter.support.spring.invoker.SingleParamSplitInvoker;
import com.lego.yxl.core.splitter.support.spring.invoker.SplitInvoker;
import com.lego.yxl.core.splitter.support.spring.invoker.SplitInvokerRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * 对 Bean 进行功能加强
 */
public class SplitInvokerProcessor implements BeanPostProcessor {

    private static final String DEFAULT_METHOD_EXECUTOR = "defaultMethodExecutor";
    private final SplitInvokerRegistry splitInvokerRegistry;

    @Autowired
    private Map<String, ParamSplitter> splitterMap;

    @Autowired
    private List<ParamSplitterBuilder> paramSplitterBuilders;

    @Autowired
    private Map<String, MethodExecutor> executorMap;

    @Autowired
    private Map<String, ResultMerger> mergerMap;


    public SplitInvokerProcessor(SplitInvokerRegistry splitInvokerRegistry) {
        this.splitInvokerRegistry = splitInvokerRegistry;
    }

    @NotNull
    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        MethodUtils.getMethodsListWithAnnotation(targetClass, Split.class).forEach(method -> {
            Split split = method.getAnnotation(Split.class);

            SplitInvoker splitInvoker = getSplitInvoker(method, split);

            this.splitInvokerRegistry.register(method, splitInvoker);
        });

        return bean;
    }

    @Nullable
    private SplitInvoker getSplitInvoker(Method method, Split split) {
        if (method.getParameterCount() == 1) {
            return buildSingleParamSplitInvoker(split, method);
        }
        return buildMultipleParamSplitInvoker(split, method);
    }

    private SplitInvoker buildMultipleParamSplitInvoker(Split split, Method method) {
        Integer index = findParamIndex(method);
        if (index == null) {
            return null;
        }

        ParamSplitter paramSplitter = findParamSplitter(method.getParameterTypes()[index], split);
        if (paramSplitter == null) {
            return null;
        }

        SplitService splitService = buildSplitService(split, method, new InvokeParamsSplitter(paramSplitter));
        if (splitService == null) {
            return null;
        }

        return new MultipleParamSplitInvoker(splitService, method, index, split.sizePrePartition());
    }

    private SplitInvoker buildSingleParamSplitInvoker(Split split, Method method) {
        ParamSplitter paramSplitter = findParamSplitter(method.getParameterTypes()[0], split);
        if (paramSplitter == null) {
            return null;
        }

        SplitService splitService = buildSplitService(split, method, paramSplitter);
        if (splitService == null) {
            return null;
        }

        return new SingleParamSplitInvoker(splitService, method, split.sizePrePartition());
    }

    private Integer findParamIndex(Method method) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(SplitParam.class)) {
                return i;
            }
        }
        return null;
    }

    private SplitService buildSplitService(Split split, Method method, ParamSplitter paramSplitter) {
        MethodExecutor methodExecutor = findMethodExecutor(split);
        if (methodExecutor == null) {
            return null;
        }

        ResultMerger resultMerger = findResultMerger(method.getReturnType(), split);
        if (resultMerger == null) {
            return null;
        }

        return new DefaultSplitService(paramSplitter, methodExecutor, resultMerger);
    }

    private MethodExecutor findMethodExecutor(Split split) {
        String config = split.executor();
        if (StringUtils.isEmpty(config)) {
            config = DEFAULT_METHOD_EXECUTOR;
        }
        return this.executorMap.get(config);
    }

    private ParamSplitter findParamSplitter(Class<?> parameterType, Split split) {
        String config = split.paramSplitter();
        if (StringUtils.isEmpty(config)) {
            for (ParamSplitter paramSplitter : splitterMap.values()) {
                if (paramSplitter instanceof SmartParamSplitter &&
                        ((SmartParamSplitter) paramSplitter).support(parameterType)) {
                    return paramSplitter;
                }
            }

            for (ParamSplitterBuilder paramSplitterBuilder : this.paramSplitterBuilders) {
                if (paramSplitterBuilder.support(parameterType)) {
                    return paramSplitterBuilder.build(parameterType);
                }
            }
            return null;
        } else {
            return this.splitterMap.get(config);
        }
    }

    private ResultMerger findResultMerger(Class<?> returnType, Split split) {
        String config = split.resultMerger();
        if (StringUtils.isEmpty(config)) {
            for (ResultMerger resultMerger : mergerMap.values()) {
                if (resultMerger instanceof SmartResultMerger &&
                        ((SmartResultMerger) resultMerger).support(returnType)) {
                    return resultMerger;
                }
            }
            return null;
        } else {
            return this.mergerMap.get(config);
        }
    }

//    private DefaultSplitService buildSplitService(Split split) {
//        MethodExecutor methodExecutor = buildMethodExecutor(split);
//        return new DefaultSplitService(this.paramSplitService, methodExecutor, this.resultMergeService);
//    }
//
//    private MethodExecutor buildMethodExecutor(Split split) {
//        ExecutorType executorType = split.executorType();
//        MethodExecutor methodExecutor = null;
//        if (executorType == ExecutorType.SERIAL){
//            methodExecutor = new SerialMethodExecutor();
//        }
//
//        if (executorType == ExecutorType.PARALLEL){
//            String executorName = split.executorName();
//            int taskPreThread = split.taskPreThread();
//            ExecutorService executorService = this.applicationContext.getBean(executorName, ExecutorService.class);
//            methodExecutor = new ParallelMethodExecutor(executorService, taskPreThread);
//        }
//        return methodExecutor;
//    }

}
