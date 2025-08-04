package com.lego.yxl.core.singlequery.jpa.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;

public class JpaFieldGreaterThanHandler
    extends AbstractJpaAnnotationHandler<FieldGreaterThan> {
    public JpaFieldGreaterThanHandler() {
        super(FieldGreaterThan.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldGreaterThan fieldGreaterThan, Object value) {
        if (value instanceof Comparable) {
            return criteriaBuilder.greaterThan(createExpression(root, fieldNameOf(fieldGreaterThan)), (Comparable) value);
        }else {
            return null;
        }
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return Comparable.class.isAssignableFrom(queryType);
    }

    @Override
    protected String fieldNameOf(FieldGreaterThan fieldGreaterThan) {
        return fieldGreaterThan.value();
    }
}
