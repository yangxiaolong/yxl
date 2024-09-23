package com.lego.yxl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 执行器名称
     * @return
     */
    String executorFactory() default "DEFAULT_EXECUTOR_FACTORY";


    /**
     * 业务组，和 key 构成唯一标识
     * @return
     */
    int group();

    /**
     * 幂等key，通过 SpEL 表达式读取参数信息
     * @return
     */
    String keyEl();

    /**
     * 幂等结果执行类型
     * @return
     */
    IdempotentHandleType handleType() default IdempotentHandleType.RESULT;
}
