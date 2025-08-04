package com.lego.yxl.core.command.support.handler.converter;

public interface SmartResultConverter<AGG, CONTEXT, RESULT>
    extends ResultConverter<AGG, CONTEXT, RESULT> {

    boolean apply(Class aggClass, Class contextClass, Class resultClass);
}
