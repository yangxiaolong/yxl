package com.lego.yxl.core;

import com.lego.yxl.core.common.ValidateErrorHandler;
import com.lego.yxl.core.common.Verifiable;

public class VerifiableBasedValidator {

    public void validate(Object target, ValidateErrorHandler validateErrorHandler) {
        if (target instanceof Verifiable) {
            ((Verifiable) target).validate(validateErrorHandler);
        }
    }
}
