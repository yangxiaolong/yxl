package com.lego.yxl.core.singlequery.jpa.support.handler;

import com.lego.yxl.core.singlequery.annotation.FieldLessThanOrEqualTo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;


public class JpaFieldLessThanOrEqualToHandler
    extends AbstractJpaAnnotationHandler<FieldLessThanOrEqualTo>{
    public JpaFieldLessThanOrEqualToHandler() {
        super(FieldLessThanOrEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldLessThanOrEqualTo fieldLessThanOrEqualTo, Object value) {
        if (value instanceof Comparable){
            return criteriaBuilder.lessThanOrEqualTo(createExpression(root, fieldNameOf(fieldLessThanOrEqualTo)), (Comparable) value);
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return Comparable.class.isAssignableFrom(queryType);
    }

    @Override
    protected String fieldNameOf(FieldLessThanOrEqualTo fieldLessThanOrEqualTo) {
        return fieldLessThanOrEqualTo.value();
    }
}
