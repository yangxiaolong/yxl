package com.lego.yxl.delay.core.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lego.yxl.delay.core.annotation.DelayBasedRocketMQ;
import com.lego.yxl.support.AbstractRocketMQSendInterceptor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 拦截方法调用，并将请求封装成 Message 发送至 RocketMQ 的 Topic
 */
@Slf4j
public class DelayMethodInterceptor extends AbstractRocketMQSendInterceptor implements MethodInterceptor {

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final Map<Method, DelayCacheItem> configCache = Maps.newConcurrentMap();

    public DelayMethodInterceptor(Environment environment, RocketMQTemplate rocketMQTemplate) {
        super(rocketMQTemplate, environment);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();

        // 1. 获取 方法上的注解信息
        DelayCacheItem delayCacheItem = this.configCache.computeIfAbsent(method, this::buildDelayCache);

        // 2. 将请求参数 转换为 MQ
        Object[] arguments = methodInvocation.getArguments();
        String argData = serialize(arguments);
        Message<String> message = MessageBuilder
                .withPayload(argData)
                .build();

        // 3. 发送 MQ
        int delayLevel = delayCacheItem.getDelayLevel();
        if (delayLevel < 0) {
            delayLevel = calDelayLevelFromEl(methodInvocation.getArguments(), delayCacheItem);
        }
        String destination = createDestination(delayCacheItem.getTopic(), delayCacheItem.getTag());
        this.rocketMQTemplate.syncSend(destination, message, 2000, delayLevel);
        log.info("success to sent Delay Task to RocketMQ for {}", Arrays.toString(arguments));
        return null;
    }

    private Integer calDelayLevelFromEl(Object[] arguments, DelayCacheItem delayCacheItem) {
        String[] parameterNames = delayCacheItem.getParameterNames();
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], arguments[i]);
        }

        return delayCacheItem.getExpression().getValue(evaluationContext, Integer.class);
    }

    private DelayCacheItem buildDelayCache(Method method) {
        DelayBasedRocketMQ delayBasedRocketMQ = AnnotatedElementUtils.findMergedAnnotation(method, DelayBasedRocketMQ.class);
        Preconditions.checkArgument(delayBasedRocketMQ != null);

        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Expression expression = expressionParser.parseExpression(delayBasedRocketMQ.delayLevelSpEl());

        String topic = resolve(delayBasedRocketMQ.topic());
        String tag = resolve(delayBasedRocketMQ.tag());
        int delayTime = delayBasedRocketMQ.delayLevel();
        String delayTimeSpEl = resolve(delayBasedRocketMQ.delayLevelSpEl());
        return new DelayCacheItem(parameterNames, expression, topic, tag, delayTime, delayTimeSpEl);
    }

    @Value
    static class DelayCacheItem {
        String[] parameterNames;
        Expression expression;
        String topic;
        String tag;
        int delayLevel;
        String delayLevelSpEl;
    }

}
