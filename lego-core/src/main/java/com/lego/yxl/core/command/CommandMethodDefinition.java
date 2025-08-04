package com.lego.yxl.core.command;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CommandMethodDefinition {
    Class contextClass();

    String methodName() default "";
}
