package com.lego.yxl.validator.core;


public abstract class AbstractParamValidator<CMD>
        extends FixTypeValidator<CMD>
        implements ParamValidator<CMD> {

    protected AbstractParamValidator(Class<CMD> cmdType) {
        super(cmdType);
    }

}
