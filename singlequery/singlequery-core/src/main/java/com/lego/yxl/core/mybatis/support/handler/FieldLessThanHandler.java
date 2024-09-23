package com.lego.yxl.core.mybatis.support.handler;

import com.lego.yxl.annotation.FieldLessThan;

import java.lang.reflect.Method;

public class FieldLessThanHandler
    extends AbstractFilterAnnotationHandler<FieldLessThan>
    implements FieldAnnotationHandler<FieldLessThan>{

    public FieldLessThanHandler() {
        super(FieldLessThan.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldLessThan lessThan, Object value) throws Exception{
        addCriteria(criteria, lessThan.value(), "LessThan", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldLessThan lessThan, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, lessThan.value(), "LessThan", valueCls);
    }
}
