package com.lego.yxl;

import lombok.Value;

@Value
public class AggNotFoundException extends RuntimeException{
    private Object id;

}
