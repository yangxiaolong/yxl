package com.lego.yxl.core.singlequery.jpa.support.handler;


import com.lego.yxl.core.singlequery.SmartComponent;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface JpaAnnotationHandler<A extends Annotation> extends SmartComponent<Annotation> {

    /**
     * 是否支持 Annotion
     * @param annotation
     * @return
     */
    @Override
    boolean support(Annotation annotation);

    /**
     * 创建 Predicate
     * @param root
     * @param query
     * @param criteriaBuilder
     * @param annotation
     * @param value
     * @param <E>
     * @return
     */
    <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, A annotation, Object value);

    /**
     * 查找 Entity 中的属性，主要用于配置检测
     * @param entityCls
     * @param a
     * @param queryType
     * @param <E>
     * @return
     */
    <E> Field findEntityField(Class<E> entityCls, A a, Class queryType);
}
