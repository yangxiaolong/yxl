package com.lego.yxl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@productRepository.getByIds(${productIds})}")
public @interface LazyLoadProductsByIds {
    String productIds();
}
