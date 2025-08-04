package com.geekhalo.like.domain.target;

import com.lego.yxl.core.loader.annotation.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.geekhalo.like.domain.target.LoadActionTargetByTarget.BEAN_NAME;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@"+ BEAN_NAME +".loadByTarget(${targetType}, ${targetId}) }")
public @interface LoadActionTargetByTarget {
    String BEAN_NAME = "actionTargetLoader";
    String targetType();
    String targetId();
}
