package com.lego.yxl.core.singlequery.mybatis.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;

import java.lang.reflect.Method;

public class FieldGreaterThanHandler
        extends AbstractFilterAnnotationHandler<FieldGreaterThan>
        implements FieldAnnotationHandler<FieldGreaterThan> {
    public FieldGreaterThanHandler() {
        super(FieldGreaterThan.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldGreaterThan greaterThan, Object value) throws Exception{
        addCriteria(criteria, greaterThan.value(), "GreaterThan", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldGreaterThan greaterThan, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, greaterThan.value(), "GreaterThan", valueCls);
    }

}
