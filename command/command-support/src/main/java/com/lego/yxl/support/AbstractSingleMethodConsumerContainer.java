package com.lego.yxl.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.core.env.Environment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Slf4j
public abstract class AbstractSingleMethodConsumerContainer
        extends AbstractConsumerContainer {
    private final Method method;

    public AbstractSingleMethodConsumerContainer(Environment environment, Object bean, Method method) {
        super(environment, bean);
        Preconditions.checkArgument(environment != null);
        Preconditions.checkArgument(bean != null);
        Preconditions.checkArgument(method != null);

        this.method = method;
    }

    protected void invokeMethod(MessageExt message) throws IllegalAccessException, InvocationTargetException {
        long now = System.currentTimeMillis();

        // 从 Message 中反序列化数据，获得方法调用参数
        byte[] body = message.getBody();

        invokeMethod(body);

        long costTime = System.currentTimeMillis() - now;
        log.info("consume message {}, cost: {} ms",  message.getMsgId(), costTime);
    }

    private void invokeMethod(byte[] body) throws IllegalAccessException, InvocationTargetException {
        String bodyAsStr = new String(body, StandardCharsets.UTF_8);

        // 先恢复 Map
        Map deserialize = SerializeUtil.deserialize(bodyAsStr, Map.class);
        Object[] params = new Object[getMethod().getParameterCount()];

        // 根据类型对每个参数进行反序列化
        for (int i = 0; i < getMethod().getParameterCount(); i++) {
            String o = (String) deserialize.get(String.valueOf(i));
            if (o == null) {
                params[i] = null;
            } else {
                params[i] = SerializeUtil.deserialize(o, getMethod().getParameterTypes()[i]);
            }
        }

        log.debug("bean is {}, method is {}, param is {}", getBean(), getMethod(), params);
        // 执行业务方法
        getMethod().invoke(getBean(), params);
    }

    public Method getMethod() {
        return this.method;
    }
}
