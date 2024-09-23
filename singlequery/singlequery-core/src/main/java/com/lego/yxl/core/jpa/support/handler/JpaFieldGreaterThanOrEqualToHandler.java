package com.lego.yxl.core.jpa.support.handler;

import com.lego.yxl.annotation.FieldGreaterThanOrEqualTo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;

public class JpaFieldGreaterThanOrEqualToHandler
        extends AbstractJpaAnnotationHandler<FieldGreaterThanOrEqualTo>{

    public JpaFieldGreaterThanOrEqualToHandler() {
        super(FieldGreaterThanOrEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldGreaterThanOrEqualTo fieldGreaterThanOrEqualTo, Object value) {
        if (value instanceof Comparable){
            return criteriaBuilder.greaterThanOrEqualTo(createExpression(root, fieldNameOf(fieldGreaterThanOrEqualTo)),
                    (Comparable) value);
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return Comparable.class.isAssignableFrom(queryType);
    }

    @Override
    protected String fieldNameOf(FieldGreaterThanOrEqualTo fieldGreaterThanOrEqualTo) {
        return fieldGreaterThanOrEqualTo.value();
    }
}
