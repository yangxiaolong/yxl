package com.lego.yxl;

import com.lego.yxl.core.common.ValidateErrorHandler;

public interface Command {
    default void validate(ValidateErrorHandler errorHandler){

    }
}
