package com.lego.yxl.core.jpa.support;

import com.lego.yxl.core.jpa.SpecificationConverter;
import com.lego.yxl.core.jpa.SpecificationConverterFactory;
import com.lego.yxl.core.jpa.support.handler.JpaAnnotationHandler;

import java.util.List;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
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
