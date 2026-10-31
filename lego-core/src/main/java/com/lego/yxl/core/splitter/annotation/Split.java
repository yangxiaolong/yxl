package com.lego.yxl.core.splitter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Split {
    int sizePrePartition() default 1000;

    String paramSplitter() default "";

    String executor() default "defaultSplitExecutor";

    String resultMerger() default "";
}
