package com.lego.yxl.core.singlequery.mybatis.support.handler;


import com.lego.yxl.core.singlequery.annotation.FieldIsNull;

import java.lang.reflect.Method;

public class FieldIsNullHandler extends AbstractFilterAnnotationHandler<FieldIsNull>
    implements FieldAnnotationHandler<FieldIsNull>{
    public FieldIsNullHandler() {
        super(FieldIsNull.class);
    }

    @Override
    public void addCriteria(Object criteria, FieldIsNull isNull, Object value) throws Exception{
        if (value instanceof Boolean) {
            if (((Boolean) value).booleanValue()) {
                addCriteria(criteria, isNull.value(), "IsNull");
            }else {
                addCriteria(criteria, isNull.value(), "IsNotNull");
            }
        }
    }

    @Override
    public Method getCriteriaMethod(Class criteriaCls, FieldIsNull isNull, Class valueCls) throws Exception {
        return getCriteriaMethod(criteriaCls, isNull.value(), "IsNull");
    }
}
