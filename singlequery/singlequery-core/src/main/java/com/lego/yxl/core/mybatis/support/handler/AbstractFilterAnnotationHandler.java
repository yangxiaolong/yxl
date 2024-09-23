package com.lego.yxl.core.mybatis.support.handler;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

abstract class AbstractFilterAnnotationHandler<A extends Annotation>
        implements FieldAnnotationHandler<A>{
    private final Class<A> aClass;

    public AbstractFilterAnnotationHandler(Class<A> aClass) {
        this.aClass = aClass;
    }
    public boolean support(A a){
        return a != null && a.annotationType() == aClass;
    }

    protected void addCriteria(Object criteria, String fieldName, String operator, Object ... value) throws Exception{
        String methodName = createFilterMethodName(fieldName, operator);
        MethodUtils.invokeMethod(criteria, true, methodName, value);
    }

    protected Method getCriteriaMethod(Class criteriaCls, String fieldName, String operator, Class ... valueCls) throws Exception{
        String methodName = createFilterMethodName(fieldName, operator);
        return MethodUtils.getMatchingMethod(criteriaCls, methodName, valueCls);
    }

    private String createFilterMethodName(String fieldName, String operator){
        return "and" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1, fieldName.length()) + operator;
    }


}
