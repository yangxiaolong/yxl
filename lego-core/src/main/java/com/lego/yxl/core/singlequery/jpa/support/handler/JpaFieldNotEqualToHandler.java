package com.lego.yxl.core.singlequery.jpa.support.handler;

import com.lego.yxl.core.singlequery.annotation.FieldNotEqualTo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;


public class JpaFieldNotEqualToHandler
    extends AbstractJpaAnnotationHandler<FieldNotEqualTo>{
    public JpaFieldNotEqualToHandler() {
        super(FieldNotEqualTo.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldNotEqualTo fieldNotEqualTo, Object value) {
        return criteriaBuilder.notEqual(createExpression(root, fieldNameOf(fieldNotEqualTo)), value);
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return field.getType() == queryType;
    }

    @Override
    protected String fieldNameOf(FieldNotEqualTo fieldNotEqualTo) {
        return fieldNotEqualTo.value();
    }
}
