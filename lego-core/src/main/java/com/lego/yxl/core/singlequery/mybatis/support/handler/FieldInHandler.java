package com.lego.yxl.core.singlequery.mybatis.support.handler;



import com.lego.yxl.core.singlequery.annotation.FieldIn;

import java.lang.reflect.Method;

public class FieldInHandler extends AbstractFilterAnnotationHandler<FieldIn>
    implements FieldAnnotationHandler<FieldIn>{
    public FieldInHandler() {
        super(FieldIn.class);
    }


    @Override
    public void addCriteria(Object criteria, FieldIn fieldIn, Object value) throws Exception{
        addCriteria(criteria, fieldIn.value(), "In", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldIn fieldIn, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, fieldIn.value(), "In", valueCls);
    }
}
