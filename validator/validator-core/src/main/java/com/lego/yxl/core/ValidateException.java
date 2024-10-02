package com.lego.yxl.core;

import lombok.Value;

@Value
public class ValidateException extends RuntimeException{
    private final String name;
    private final String code;
    private final String msg;
}
