package com.geekhalo.like.domain.user;

import com.lego.yxl.loader.core.annotation.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.geekhalo.like.domain.user.LoadActionUserByUserId.BEAN_NAME;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@" + BEAN_NAME + ".loadByUserId(${userId}) }")
public @interface LoadActionUserByUserId {
    String BEAN_NAME = "actionUserLoader";

    String userId();
}
