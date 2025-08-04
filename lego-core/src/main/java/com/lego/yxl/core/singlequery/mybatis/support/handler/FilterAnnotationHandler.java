package com.lego.yxl.core.singlequery.mybatis.support.handler;

import java.lang.annotation.Annotation;

public interface FilterAnnotationHandler<A extends Annotation> {
    String getFieldValue(A annotation);

    String getOperator();

    default boolean hasParam(){
        return true;
    }
}
