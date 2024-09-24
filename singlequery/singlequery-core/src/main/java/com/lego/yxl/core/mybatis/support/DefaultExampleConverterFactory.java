package com.lego.yxl.core.mybatis.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.core.mybatis.ExampleConverter;
import com.lego.yxl.core.mybatis.ExampleConverterFactory;
import com.lego.yxl.core.mybatis.support.handler.FieldAnnotationHandler;

import java.util.List;


public class DefaultExampleConverterFactory implements ExampleConverterFactory {
    private final List<FieldAnnotationHandler> fieldAnnotationHandlers;

    public DefaultExampleConverterFactory(List<FieldAnnotationHandler> fieldAnnotationHandlers) {
        Preconditions.checkArgument(fieldAnnotationHandlers != null);
        this.fieldAnnotationHandlers = fieldAnnotationHandlers;
    }

    @Override
    public ExampleConverter createFor(Class example) {
        return new ReflectBasedExampleConverter(example, this.fieldAnnotationHandlers);
    }
}
