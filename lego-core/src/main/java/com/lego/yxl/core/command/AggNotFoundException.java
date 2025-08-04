package com.lego.yxl.core.command;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AggNotFoundException extends RuntimeException{
    private Object id;

}
