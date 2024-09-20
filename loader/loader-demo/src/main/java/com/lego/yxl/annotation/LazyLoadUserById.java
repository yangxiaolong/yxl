package com.lego.yxl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 只能标记在 字段上
@Target({ElementType.FIELD})
// 运行时生效
@Retention(RetentionPolicy.RUNTIME)
// LazyLoadBy 注解
@LazyLoadBy("#{@userRepository.getById(${userId})}")
public @interface LazyLoadUserById {

    String userId();
}
