package com.lego.yxl.command.core.support.handler.converter;

public class BooleanResultConverter implements SmartResultConverter<Object, Object, Boolean> {
    @Override
    public Boolean convert(Object o, Object o2) {
        return true;
    }

    @Override
    public boolean apply(Class aggClass, Class contextClass, Class resultClass) {
        return Boolean.class.equals(resultClass) || Boolean.TYPE.equals(resultClass);
    }

    @Override
    public String toString(){
        return getClass().toString();
    }
}
