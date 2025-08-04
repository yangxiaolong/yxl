package com.lego.yxl.core.singlequery.mybatis.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldGreaterThanOrEqualTo;

import java.lang.reflect.Method;

public class FieldGreaterThanOrEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldGreaterThanOrEqualTo>
    implements FieldAnnotationHandler<FieldGreaterThanOrEqualTo>{
    public FieldGreaterThanOrEqualToHandler() {
        super(FieldGreaterThanOrEqualTo.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldGreaterThanOrEqualTo greaterThanOrEqualTo, Object value) throws Exception{
        addCriteria(criteria, greaterThanOrEqualTo.value(), "GreaterThanOrEqualTo", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldGreaterThanOrEqualTo greaterThanOrEqualTo, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, greaterThanOrEqualTo.value(), "GreaterThanOrEqualTo", valueCls);
    }
}
