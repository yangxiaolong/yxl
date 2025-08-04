package com.lego.yxl.core.singlequery.mybatis.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldEqualTo;

import java.lang.reflect.Method;

public class FieldEqualToHandler
        extends AbstractFilterAnnotationHandler<FieldEqualTo>
        implements FieldAnnotationHandler<FieldEqualTo> {

    public FieldEqualToHandler() {
        super(FieldEqualTo.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldEqualTo fieldEqualTo, Object value) throws Exception{
        addCriteria(criteria, fieldEqualTo.value(), "EqualTo", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldEqualTo fieldEqualTo, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, fieldEqualTo.value(), "EqualTo", valueCls);
    }
}
