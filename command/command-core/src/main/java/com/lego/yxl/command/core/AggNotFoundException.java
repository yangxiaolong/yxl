package com.lego.yxl.command.core;

import lombok.Value;

@Value
public class AggNotFoundException extends RuntimeException{
    private Object id;

}
