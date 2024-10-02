package com.lego.yxl.core;


public abstract class AbstractParamValidator<CMD>
        extends FixTypeValidator<CMD>
        implements ParamValidator<CMD> {

    protected AbstractParamValidator(Class<CMD> cmdType) {
        super(cmdType);
    }

}
