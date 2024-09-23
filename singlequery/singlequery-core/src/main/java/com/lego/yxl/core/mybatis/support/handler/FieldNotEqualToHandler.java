package com.lego.yxl.core.mybatis.support.handler;


import com.lego.yxl.annotation.FieldNotEqualTo;

import java.lang.reflect.Method;

public class FieldNotEqualToHandler
    extends AbstractFilterAnnotationHandler<FieldNotEqualTo>
    implements FieldAnnotationHandler<FieldNotEqualTo>{
    public FieldNotEqualToHandler() {
        super(FieldNotEqualTo.class);
    }



    @Override
    public void addCriteria(Object criteria, FieldNotEqualTo notEqualTo, Object value) throws Exception{
        addCriteria(criteria, notEqualTo.value(), "NotEqualTo", value);
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldNotEqualTo notEqualTo, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, notEqualTo.value(), "NotEqualTo", valueCls);
    }
}
