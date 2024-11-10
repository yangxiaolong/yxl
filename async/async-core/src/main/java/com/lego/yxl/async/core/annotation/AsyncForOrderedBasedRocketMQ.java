package com.lego.yxl.async.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncForOrderedBasedRocketMQ {

    /**
     * 是否可用
     * @return
     */
    String enable() default "true";

    /**
     * MQ topic
     * @return
     */
    String topic();

    /**
     * MQ tag
     * @return
     */
    String tag() default "*";

    /**
     * 顺序消费设置的 hashKey
     * @return
     */
    String shardingKey();

    /**
     * 消费组
     * @return
     */
    String consumerGroup();

    /**
     * nameServer 配置
     * @return
     */
    String nameServer() default "${rocketmq.name-server:}";

    /**
     * 消费者运行的 profile，主要用于发送和消费分离的场景
     * @return
     */
    String consumerProfile() default "";

}

