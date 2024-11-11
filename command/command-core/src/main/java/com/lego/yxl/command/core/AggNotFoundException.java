package com.lego.yxl.command.core;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AggNotFoundException extends RuntimeException{
    private Object id;

}
