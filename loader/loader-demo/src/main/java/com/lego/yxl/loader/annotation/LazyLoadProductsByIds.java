package com.lego.yxl.loader.annotation;

import com.lego.yxl.loader.core.annotation.LazyLoadBy;

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
