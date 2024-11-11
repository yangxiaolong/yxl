package com.lego.yxl.command.core.support.handler.converter;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ResultConverterNotFoundException extends RuntimeException{
    private final Class aggClass;
    private final Class contextClass;
    private final Class resultClass;
}
