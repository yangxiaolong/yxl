package com.lego.yxl.command.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CommandMethodDefinition {
    Class contextClass();

    String methodName() default "";
}
