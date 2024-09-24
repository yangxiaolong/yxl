package com.lego.yxl.core.jpa.support;

import com.lego.yxl.core.jpa.SpecificationConverter;
import com.lego.yxl.core.jpa.SpecificationConverterFactory;
import com.lego.yxl.core.jpa.support.handler.JpaAnnotationHandler;

import java.util.List;

public class DefaultSpecificationConverterFactory implements SpecificationConverterFactory {
    private final List<JpaAnnotationHandler> annotationHandlers;

    public DefaultSpecificationConverterFactory(List<JpaAnnotationHandler> annotationHandlers) {
        this.annotationHandlers = annotationHandlers;
    }

    @Override
    public <E> SpecificationConverter<E> createForEntity(Class<E> entityCls) {
        return new DefaultSpecificationConverter<>(entityCls, this.annotationHandlers);
    }
}
