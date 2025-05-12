package com.lego.yxl.async.core.order;

import com.google.common.collect.Maps;
import com.lego.yxl.async.core.annotation.AsyncForOrderedBasedRocketMQ;
import com.lego.yxl.support.AbstractRocketMQSendInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
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

import static org.apache.commons.lang3.BooleanUtils.toBoolean;

/**
 * 拦截方法调用，将其转发至 MQ 中 <br />
 * 1. 从方法上读取 AsyncBasedRocketMQ，获取配置信息 <br />
 * 2. 将入参进行序列化 <br />
 * 3. 调用 MQ 接口，发送消息 <br />
 */
@Slf4j
public class OrderedAsyncInterceptor extends AbstractRocketMQSendInterceptor implements MethodInterceptor {

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final Map<Method, InvokeCacheItem> invokeCache = Maps.newConcurrentMap();

    public OrderedAsyncInterceptor(Environment environment, RocketMQTemplate rocketMQTemplate) {
        super(rocketMQTemplate, environment);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();

        // 1. 从方法中解析注解信息
        InvokeCacheItem invokeCacheItem = this.invokeCache.computeIfAbsent(method, this::parseMethod);

        // 未启用，直接调用被注解方法
        if (!invokeCacheItem.isEnable()) {
            return methodInvocation.proceed();
        }

        // 2. 将请求参数 转换为 MQ
        Object[] arguments = methodInvocation.getArguments();
        String argData = serialize(arguments);
        log.info("After serialize, data is {}", argData);


        // 从参数中读取 shardingKey
        String[] parameterNames = invokeCacheItem.getParameterNames();
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], arguments[i]);
        }

        Object value = invokeCacheItem.getExpression().getValue(evaluationContext);
        String shardingKey = String.valueOf(value);

        // 构建 Message
        Message<String> msg = MessageBuilder
                .withPayload(argData)
                .build();

        // 3. 发送 MQ
        String destination = createDestination(invokeCacheItem.getTopic(), invokeCacheItem.getTag());
        SendResult sendResult = this.getRocketMQTemplate().syncSendOrderly(destination, msg, shardingKey, 2000);

        log.info("success to send orderly async Task to RocketMQ, args is {}, shardingKey is {}, msg is {}, result is {}",
                Arrays.toString(arguments),
                shardingKey,
                msg,
                sendResult);
        return null;
    }

    private InvokeCacheItem parseMethod(Method method) {
        AsyncForOrderedBasedRocketMQ asyncForOrderedBasedRocketMQ = method.getAnnotation(AsyncForOrderedBasedRocketMQ.class);

        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Expression expression = expressionParser.parseExpression(asyncForOrderedBasedRocketMQ.shardingKey());

        boolean enable = toBoolean(resolve(asyncForOrderedBasedRocketMQ.enable()));

        String topic = resolve(asyncForOrderedBasedRocketMQ.topic());
        String tag = resolve(asyncForOrderedBasedRocketMQ.tag());

        return new InvokeCacheItem(
                enable,
                parameterNames,
                expression,
                topic,
                tag);
    }

    @Data
    static class InvokeCacheItem {
        private final boolean enable;
        private final String[] parameterNames;
        private final Expression expression;
        private final String topic;
        private final String tag;
    }

}
