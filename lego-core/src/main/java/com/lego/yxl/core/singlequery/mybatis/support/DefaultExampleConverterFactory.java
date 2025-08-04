package com.lego.yxl.core.singlequery.mybatis.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.core.singlequery.mybatis.ExampleConverter;
import com.lego.yxl.core.singlequery.mybatis.ExampleConverterFactory;
import com.lego.yxl.core.singlequery.mybatis.support.handler.FieldAnnotationHandler;

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
