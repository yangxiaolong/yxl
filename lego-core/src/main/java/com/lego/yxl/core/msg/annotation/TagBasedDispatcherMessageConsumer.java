package com.lego.yxl.core.msg.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TagBasedDispatcherMessageConsumer {
    /**
     * Topic
     * @return
     */
    String topic();

    /**
     * 消费者组名称
     * @return
     */
    String consumer();

    /**
     * 命名服务
     * @return
     */
    String nameServer() default "${rocketmq.name-server:}";

}
