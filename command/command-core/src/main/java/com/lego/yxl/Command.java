package com.lego.yxl;

import com.lego.yxl.common.ValidateErrorHandler;

public interface Command {
    default void validate(ValidateErrorHandler errorHandler){

    }
}
