package com.lego.yxl.joininmemory.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinInMemoryConfig {
    JoinInMemeoryExecutorType executorType() default JoinInMemeoryExecutorType.SERIAL;

    String executorName() default "defaultExecutor";
}
