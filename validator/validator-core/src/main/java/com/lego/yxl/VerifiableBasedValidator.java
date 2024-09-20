package com.lego.yxl;

import com.lego.yxl.common.ValidateErrorHandler;
import com.lego.yxl.common.Verifiable;

public class VerifiableBasedValidator {

    public void validate(Object target, ValidateErrorHandler validateErrorHandler) {
        if (target instanceof Verifiable){
            ((Verifiable) target).validate(validateErrorHandler);
        }
    }
}
