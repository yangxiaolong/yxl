package com.lego.yxl.command.core.support.handler.converter;

@FunctionalInterface
public interface ResultConverter<AGG, CONTEXT, RESULT> {
    RESULT convert(AGG agg, CONTEXT context);
}
