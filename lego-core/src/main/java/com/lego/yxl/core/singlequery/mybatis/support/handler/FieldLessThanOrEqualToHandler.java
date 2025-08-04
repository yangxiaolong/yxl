package com.lego.yxl.core.singlequery.mybatis.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldLessThanOrEqualTo;

import java.lang.reflect.Method;

public class FieldLessThanOrEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldLessThanOrEqualTo>
    implements FieldAnnotationHandler<FieldLessThanOrEqualTo>{

    public FieldLessThanOrEqualToHandler() {
        super(FieldLessThanOrEqualTo.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldLessThanOrEqualTo lessThanOrEqualTo, Object value) throws Exception{
        addCriteria(criteria, lessThanOrEqualTo.value(), "LessThanOrEqualTo", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldLessThanOrEqualTo lessThanOrEqualTo, Class valueCls) throws Exception{
        return getCriteriaMethod(criteriaCls, lessThanOrEqualTo.value(), "LessThanOrEqualTo", valueCls);
    }
}
