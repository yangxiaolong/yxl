package com.lego.yxl.command.core.support.handler.converter;

import lombok.Value;

@Value
public class ResultConverterNotFoundException extends RuntimeException{
    private final Class aggClass;
    private final Class contextClass;
    private final Class resultClass;
}
