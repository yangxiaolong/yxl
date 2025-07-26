package com.lego.yxl.wide;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindFrom {

    /**
     * 来源
     *
     * @return
     */
    Class sourceClass();

    /**
     * 字段名称
     *
     * @return
     */
    String field();
}
