package com.lego.yxl.core.faultrecovery.annotation;

import com.lego.yxl.core.faultrecovery.smart.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    ActionType type();
}
