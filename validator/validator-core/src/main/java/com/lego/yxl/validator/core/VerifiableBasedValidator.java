package com.lego.yxl.validator.core;

import com.lego.yxl.validator.core.common.ValidateErrorHandler;
import com.lego.yxl.validator.core.common.Verifiable;

public class VerifiableBasedValidator {

    public void validate(Object target, ValidateErrorHandler validateErrorHandler) {
        if (target instanceof Verifiable) {
            ((Verifiable) target).validate(validateErrorHandler);
        }
    }
}
