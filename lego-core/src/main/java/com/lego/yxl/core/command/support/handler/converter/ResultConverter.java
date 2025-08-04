package com.lego.yxl.core.command.support.handler.converter;

@FunctionalInterface
public interface ResultConverter<AGG, CONTEXT, RESULT> {
    RESULT convert(AGG agg, CONTEXT context);
}
