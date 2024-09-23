package com.lego.yxl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxResult {
    int DEF_MAX = 5000;
    MaxResultCheckStrategy DEF_STRATEGY = MaxResultCheckStrategy.LOG;

    int max() default  DEF_MAX;

    MaxResultCheckStrategy strategy() default MaxResultCheckStrategy.LOG;
}
