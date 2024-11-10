package com.lego.yxl.command.core.support.handler.contextfactory;

import lombok.Value;

@Value
public class ContextFactoryNotFoundException extends RuntimeException{
    private Class cmdClass;
    private Class contextClass;
}
