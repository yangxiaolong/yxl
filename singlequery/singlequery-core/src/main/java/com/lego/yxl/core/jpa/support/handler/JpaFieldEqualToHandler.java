package com.lego.yxl.core.jpa.support.handler;

import com.lego.yxl.annotation.FieldEqualTo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaFieldEqualToHandler
        extends AbstractJpaAnnotationHandler<FieldEqualTo>
        implements JpaAnnotationHandler<FieldEqualTo>{

    public JpaFieldEqualToHandler() {
        super(FieldEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldEqualTo fieldEqualTo, Object value) {
        return criteriaBuilder.equal(createExpression(root, fieldNameOf(fieldEqualTo)), value);
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return queryType == field.getType();
    }

    @Override
    protected String fieldNameOf(FieldEqualTo fieldEqualTo) {
        return fieldEqualTo.value();
    }
}
