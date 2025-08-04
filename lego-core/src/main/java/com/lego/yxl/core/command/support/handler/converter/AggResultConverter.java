package com.lego.yxl.core.command.support.handler.converter;

import com.lego.yxl.core.command.support.AggRoot;

public class AggResultConverter<AGG extends AggRoot>
        implements ResultConverter<AGG, Object, AGG> {
    private static final AggResultConverter INSTANCE = new AggResultConverter();

    private AggResultConverter(){

    }

    @Override
    public AGG convert(AGG agg, Object o) {
        return agg;
    }

    public static AggResultConverter getInstance(){
        return INSTANCE;
    }
}
