package com.lego.yxl.validator.core;

import com.lego.yxl.validator.core.common.ValidateErrorHandler;
import com.lego.yxl.validator.core.common.ValidateErrors;
import lombok.Getter;

public class ErrorsCollector implements ValidateErrorHandler {
    @Getter
    private ValidateErrors validateErrors;

    @Override
    public void handleError(String name, String code, String msg) {
        getOrCreateValidateErrors().addError(name, code, msg);
    }

    private ValidateErrors getOrCreateValidateErrors(){
        if (this.validateErrors == null){
            this.validateErrors = new ValidateErrors();
        }
        return this.validateErrors;
    }
}