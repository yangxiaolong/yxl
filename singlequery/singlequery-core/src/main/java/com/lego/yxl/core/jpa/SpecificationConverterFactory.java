package com.lego.yxl.core.jpa;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SpecificationConverterFactory {
    <E> SpecificationConverter<E> createForEntity(Class<E> entityCls);
}
