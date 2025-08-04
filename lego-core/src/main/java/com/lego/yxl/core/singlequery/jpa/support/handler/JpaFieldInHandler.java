package com.lego.yxl.core.singlequery.jpa.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldIn;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;
import java.util.Collection;

public class JpaFieldInHandler
    extends AbstractJpaAnnotationHandler<FieldIn>{
    public JpaFieldInHandler() {
        super(FieldIn.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldIn fieldIn, Object value) {
        if (value instanceof Collection) {
            return criteriaBuilder.in(createExpression(root, fieldNameOf(fieldIn)))
                    .value((Collection<?>) value);
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return Collection.class.isAssignableFrom(queryType);
    }

    @Override
    protected String fieldNameOf(FieldIn fieldIn) {
        return fieldIn.value();
    }
}
