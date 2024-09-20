package com.lego.yxl.support.handler.converter;

import com.lego.yxl.AggRoot;

public class AggSmartResultConverter<AGG extends AggRoot>
        implements SmartResultConverter<AGG, Object, AGG> {

    @Override
    public AGG convert(AGG agg, Object o) {
        return agg;
    }

    @Override
    public boolean apply(Class aggClass, Class contextClass, Class resultClass) {
        return aggClass.equals(resultClass);
    }

    @Override
    public String toString(){
        return getClass().toString();
    }
}
