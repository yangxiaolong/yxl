package com.lego.yxl.core.jpa.support.handler;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractJpaAnnotationHandler<A extends Annotation> implements JpaAnnotationHandler<A>{
    private final Class<A> annCls;

    public AbstractJpaAnnotationHandler(Class<A> annCls) {
        this.annCls = annCls;
    }

    @Override
    public boolean support(Annotation annotation) {
        return annotation != null && annotation.annotationType() == this.annCls;
    }

    @Override
    public <E> Field findEntityField(Class<E> entityCls, A a, Class queryType) {
        String fieldName = fieldNameOf(a);
        Field field = FieldUtils.getField(entityCls, fieldName, true);
        if (field == null){
            return null;
        }
        if (matchField(field, queryType)){
            return field;
        }
        return null;
    }

    protected abstract boolean matchField(Field field, Class queryType);

    protected abstract String fieldNameOf(A a);

    protected <E> Expression createExpression(Root<E> root, String path){
        Path<E> result = null;
        for (String fieldName : path.split("\\.")){
            if (result == null) {
                result = root.get(fieldName);
            }else {
                result = result.get(fieldName);
            }
        }
        return result;
    }
}
