package com.lego.yxl.core.jpa;

public interface SpecificationConverterFactory {
    <E> SpecificationConverter<E> createForEntity(Class<E> entityCls);
}
