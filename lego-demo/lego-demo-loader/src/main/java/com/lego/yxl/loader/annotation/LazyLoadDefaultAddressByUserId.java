package com.lego.yxl.loader.annotation;

import com.lego.yxl.core.loader.annotation.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@addressRepository.getDefaultAddressByUserId(${userId})}")
public @interface LazyLoadDefaultAddressByUserId {
    String userId();
}
