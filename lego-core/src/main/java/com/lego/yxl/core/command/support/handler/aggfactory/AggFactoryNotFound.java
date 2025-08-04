package com.lego.yxl.core.command.support.handler.aggfactory;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AggFactoryNotFound extends RuntimeException{
    private Class contextClass;
    private Class aggClass;
}
