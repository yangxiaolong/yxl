package com.lego.yxl.core.jpa.support.handler;
import com.lego.yxl.annotation.FieldIsNull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;


public class JpaFieldIsNullHandler
    extends AbstractJpaAnnotationHandler<FieldIsNull>{
    public JpaFieldIsNullHandler() {
        super(FieldIsNull.class);
    }

    @Override
    public <E> Predicate create(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FieldIsNull fieldIsNull, Object value) {
        if (value instanceof Boolean){
            if ((Boolean) value){
                return criteriaBuilder.isNull(createExpression(root, fieldNameOf(fieldIsNull)));
            }else {
                return criteriaBuilder.isNotNull(createExpression(root, fieldNameOf(fieldIsNull)));
            }
        }
        return null;
    }

    @Override
    protected boolean matchField(Field field, Class queryType) {
        return true;
    }

    @Override
    protected String fieldNameOf(FieldIsNull fieldIsNull) {
        return fieldIsNull.value();
    }
}
