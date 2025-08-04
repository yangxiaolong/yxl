package com.lego.yxl.core.faultrecovery.executor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lego.yxl.core.faultrecovery.annotation.SmartFault;
import com.lego.yxl.core.faultrecovery.smart.ActionType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.RecoverAnnotationRecoveryHandler;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class SmartFaultExecutor {
    private final SmartFault smartFault;
    private final Method methodBySmartFault;
    private final ActionTypeProvider actionTypeProvider;
    private final ExceptionMapProvider exceptionMapProvider;

    private final RetryTemplate retryTemplate;
    private final RetryTemplate fallbackTemplate;

    public SmartFaultExecutor(SmartFault smartFault,
                              Method methodBySmartFault,
                              ActionTypeProvider actionTypeProvider,
                              ExceptionMapProvider exceptionMapProvider) {

        Preconditions.checkArgument(smartFault != null);
        Preconditions.checkArgument(methodBySmartFault != null);
        Preconditions.checkArgument(actionTypeProvider != null);
        Preconditions.checkArgument(exceptionMapProvider != null);

        this.smartFault = smartFault;
        this.methodBySmartFault = methodBySmartFault;
        this.actionTypeProvider = actionTypeProvider;
        this.exceptionMapProvider = exceptionMapProvider;

        this.retryTemplate = buildRetryTemplate();
        this.fallbackTemplate = buildFallbackTemplate();
    }

    private RetryTemplate buildFallbackTemplate() {
        RetryTemplate fallbackTemplate = new RetryTemplate();
        fallbackTemplate.setRetryPolicy(new NeverRetryPolicy());
        return fallbackTemplate;
    }

    private RetryTemplate buildRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        int maxRetryCount = this.smartFault.maxRetry();
        maxRetryCount = maxRetryCount <= 0 ? 3 : maxRetryCount;

        Map<Class<? extends Throwable>, Boolean> retryableExceptions = buildExceptions();

        boolean retryNotExcluded = this.smartFault.include().length == 0;

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(maxRetryCount,
                retryableExceptions, false, retryNotExcluded);

        retryTemplate.setRetryPolicy(retryPolicy);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

    private Map<Class<? extends Throwable>, Boolean> buildExceptions() {

        Map<Class<? extends Throwable>, Boolean> result = Maps.newHashMap();
        Map<Class<? extends Throwable>, Boolean> defExceptionMap = this.exceptionMapProvider.get();
        if (defExceptionMap != null) {
            result.putAll(defExceptionMap);
        }

        for (Class<? extends Throwable> type : this.smartFault.include()) {
            result.put(type, true);
        }
        for (Class<? extends Throwable> type : this.smartFault.exclude()) {
            result.put(type, false);
        }

        return result;
    }

    private MethodInvocationRecoverer<?> findRecoverHandler(Object target) {
        if (target instanceof MethodInvocationRecoverer) {
            return (MethodInvocationRecoverer<?>) target;
        }
        final AtomicBoolean foundRecoverable = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(target.getClass(), method -> {
            if (AnnotationUtils.findAnnotation(method, Recover.class) != null) {
                foundRecoverable.set(true);
            }
        });

        if (!foundRecoverable.get()) {
            return null;
        }
        return new RecoverAnnotationRecoveryHandler<>(target, this.methodBySmartFault);
    }

    public Object invoke(MethodInvocation joinPoint) throws Throwable {
        if (actionTypeProvider == null) {
            log.warn("action type provider lost!!!!");
            return joinPoint.proceed();
        }

        ActionType actionType = this.actionTypeProvider.get();
        if (actionType == null) {
            log.warn("action type is null!!!!");
            return joinPoint.proceed();
        }

        log.info("action type is {}", actionType);
        return switch (actionType) {
            case QUERY -> fallback(joinPoint);
            case COMMAND -> retry(joinPoint);
        };
    }

    private Object retry(MethodInvocation joinPoint) throws Throwable {
        log.debug("run retry for method {}", joinPoint);
        return retryTemplate.execute(new RetryInvoker(joinPoint));
    }

    private Object fallback(MethodInvocation joinPoint) throws Throwable {
        log.debug("run fallback for method {}", joinPoint);
        RetryInvoker retryInvoker = new RetryInvoker(joinPoint);
        MethodInvocationRecoverer<?> recoverHandler = findRecoverHandler(joinPoint.getThis());
        if (recoverHandler != null) {
            return fallbackTemplate.execute(retryInvoker, context -> {
                log.warn("recover From ERROR for method {}", joinPoint);
                return recoverHandler.recover(joinPoint.getArguments(), context.getLastThrowable());
            });
        }

        return fallbackTemplate.execute(retryInvoker);
    }

    private static class RetryInvoker implements RetryCallback<Object, Throwable> {
        private final MethodInvocation joinPoint;

        private RetryInvoker(MethodInvocation joinPoint) {
            this.joinPoint = joinPoint;
        }

        @Override
        public Object doWithRetry(RetryContext context) throws Throwable {
            if (context.getRetryCount() > 0) {
                Method method = joinPoint.getMethod();
                log.info("Retry method {} use {}", method, joinPoint.getArguments());
            }
            return joinPoint.proceed();
        }
    }
}
