package com.lego.yxl.core.validator;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ValidateException extends RuntimeException{
    private final String name;
    private final String code;
    private final String msg;
}
