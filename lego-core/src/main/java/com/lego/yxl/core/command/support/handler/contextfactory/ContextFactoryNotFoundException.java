package com.lego.yxl.core.command.support.handler.contextfactory;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ContextFactoryNotFoundException extends RuntimeException{
    private Class cmdClass;
    private Class contextClass;
}
