package com.lego.yxl.command.core.support.handler.contextfactory;

public class EqualsContextFactory<O> implements ContextFactory<O, O> {
    private static final EqualsContextFactory INSTANCE = new EqualsContextFactory();

    private EqualsContextFactory(){

    }

    @Override
    public O create(O o) {
        return o;
    }

    public static EqualsContextFactory getInstance(){
        return INSTANCE;
    }
}
