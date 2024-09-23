package com.lego.yxl.core.mybatis.support.handler;


import com.lego.yxl.annotation.FieldNotIn;

import java.lang.reflect.Method;

public class FieldNotInHandler
    extends AbstractFilterAnnotationHandler<FieldNotIn>
    implements FieldAnnotationHandler<FieldNotIn>{
    public FieldNotInHandler() {
        super(FieldNotIn.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldNotIn notIn, Object value) throws Exception{
        addCriteria(criteria, notIn.value(), "NotIn", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldNotIn notIn, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, notIn.value(), "NotIn", valueCls);
    }
}
