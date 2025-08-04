package com.lego.yxl.core.singlequery.jpa;

public interface SpecificationConverterFactory {
    <E> SpecificationConverter<E> createForEntity(Class<E> entityCls);
}
