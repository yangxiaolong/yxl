package com.lego.yxl;

import com.lego.yxl.common.ValidateErrorHandler;

public interface BaseValidator<A> extends SmartComponent<A> {
    void validate(A a, ValidateErrorHandler validateErrorHandler);

    default void validate(A a){
        validate(a, ((name, code, msg) -> {
            throw new ValidateException(name, code, msg);
        }));
    }
}
