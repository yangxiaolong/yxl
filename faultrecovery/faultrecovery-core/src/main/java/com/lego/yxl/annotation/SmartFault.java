package com.lego.yxl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SmartFault {
    String recover() default "";

    int maxRetry() default 3;

    /**
     * 需要重试的异常信息
     *
     * @return Class
     */
    Class<? extends Throwable>[] include() default {};

    /**
     * 不需要重试的异常信息
     *
     * @return Class
     */
    Class<? extends Throwable>[] exclude() default {};
}
