package com.lego.yxl.core.singlequery.jpa.support.handler;
import com.lego.yxl.core.singlequery.annotation.FieldLessThan;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;


public class JpaFieldLessThanHandler
    extends AbstractJpaAnnotationHandler<FieldLessThan>{
    public JpaFieldLessThanHandler() {
        super(FieldLessThan.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldLessThan fieldLessThan, Object value) {
        if (value instanceof Comparable){
            return criteriaBuilder.lessThan(createExpression(root, fieldNameOf(fieldLessThan)), (Comparable) value);
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return Comparable.class.isAssignableFrom(queryType);
    }

    @Override
    protected String fieldNameOf(FieldLessThan fieldLessThan) {
        return fieldLessThan.value();
    }
}
