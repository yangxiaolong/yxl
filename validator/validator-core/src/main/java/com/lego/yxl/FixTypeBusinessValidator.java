package com.lego.yxl;

import java.lang.reflect.ParameterizedType;

public abstract class FixTypeBusinessValidator<A> implements BusinessValidator<A> {
    private final Class<A> type;

    protected FixTypeBusinessValidator(){
        Class<A> type = (Class<A>)((ParameterizedType)getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.type = type;
    }

    protected FixTypeBusinessValidator(Class<A> type) {
        this.type = type;
    }

    @Override
    public final boolean support(Object a) {
        return this.type.isInstance(a);
    }

}
