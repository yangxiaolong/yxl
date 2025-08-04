package com.lego.yxl.core.async.normal;

import com.google.common.collect.Maps;
import com.lego.yxl.core.async.annotation.AsyncBasedRocketMQ;
import com.lego.yxl.core.singlequery.support.AbstractRocketMQSendInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 拦截方法调用，将其转发至 MQ 中 <br />
 * 1. 从方法上读取 AsyncBasedRocketMQ，获取配置信息 <br />
 * 2. 将入参进行序列化 <br />
 * 3. 调用 MQ 接口，发送消息 <br />
 */

@Slf4j
public class NormalAsyncInterceptor extends AbstractRocketMQSendInterceptor implements MethodInterceptor {
    private final Map<Method, InvokeCacheItem> invokeCache = Maps.newConcurrentMap();

    public NormalAsyncInterceptor(Environment environment, RocketMQTemplate rocketMQTemplate) {
        super(rocketMQTemplate, environment);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();

        // 1. 获取 方法上的注解信息
        InvokeCacheItem invokeCacheItem = this.invokeCache.computeIfAbsent(method, this::parseMethod);

        // 2. 将请求参数 转换为 MQ
        Object[] arguments = methodInvocation.getArguments();
        String argData = serialize(arguments);

        log.info("After serialize, data is {}", argData);

        // 封装为 Message 对象
        Message<String> msg = MessageBuilder
                .withPayload(argData)
                .build();

        // 3. 发送 MQ
        String destination = createDestination(invokeCacheItem.getTopic(), invokeCacheItem.getTag());
        SendResult sendResult = this.getRocketMQTemplate().syncSend(destination, msg, 2000);

        log.info("success to send async Task to RocketMQ, args is {}, msg is {}, result is {}",
                Arrays.toString(arguments),
                msg,
                sendResult);
        return null;
    }

    private InvokeCacheItem parseMethod(Method method) {
        AsyncBasedRocketMQ asyncBasedRocketMQ = method.getAnnotation(AsyncBasedRocketMQ.class);

        String topic = this.resolve(asyncBasedRocketMQ.topic());
        String tag = this.resolve(asyncBasedRocketMQ.tag());

        return new InvokeCacheItem(asyncBasedRocketMQ.nameServer(), topic, tag);
    }

    @Data
    static class InvokeCacheItem {
        private final String instanceName;
        private final String topic;
        private final String tag;
    }

}
