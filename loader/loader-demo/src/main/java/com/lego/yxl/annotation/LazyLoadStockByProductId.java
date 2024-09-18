package com.lego.yxl.annotation;

import com.lego.yxl.core.annotation.LazyLoadBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@LazyLoadBy("#{@stockRepository.getByProductId(${productId})}")
public @interface LazyLoadStockByProductId {
    String productId();
}