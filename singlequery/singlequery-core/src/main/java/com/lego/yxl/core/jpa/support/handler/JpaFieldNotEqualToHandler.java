package com.lego.yxl.core.jpa.support.handler;

import com.lego.yxl.annotation.FieldNotEqualTo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
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
