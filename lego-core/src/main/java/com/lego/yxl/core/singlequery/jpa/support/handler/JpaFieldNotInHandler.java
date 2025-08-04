package com.lego.yxl.core.singlequery.jpa.support.handler;

import com.lego.yxl.core.singlequery.annotation.FieldNotIn;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;
import java.util.Collection;


public class JpaFieldNotInHandler
    extends AbstractJpaAnnotationHandler<FieldNotIn>{
    public JpaFieldNotInHandler() {
        super(FieldNotIn.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldNotIn fieldNotIn, Object value) {
        if (value instanceof Collection){
            return criteriaBuilder.in(createExpression(root, fieldNameOf(fieldNotIn)))
                    .value((Collection<?>) value).not();
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return Collection.class.isAssignableFrom(queryType);
    }

    @Override
    protected String fieldNameOf(FieldNotIn fieldNotIn) {
        return fieldNotIn.value();
    }
}
