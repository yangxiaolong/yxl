package com.geekhalo.like.domain;

import com.lego.yxl.core.loader.annotation.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.geekhalo.like.domain.LazyLoadByUserId.BEAN_NAME;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@"+ BEAN_NAME +".{loadMethodName}(${param1})}")
public @interface LazyLoadByUserId {
    String BEAN_NAME = "{BeanName}";
    String param1();
}
