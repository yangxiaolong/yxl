package com.lego.yxl.core.command;

import com.lego.yxl.core.validator.common.ValidateErrorHandler;

public interface Command {
    default void validate(ValidateErrorHandler errorHandler){

    }
}
