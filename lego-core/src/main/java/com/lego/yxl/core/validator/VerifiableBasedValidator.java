package com.lego.yxl.core.validator;

import com.lego.yxl.core.validator.common.ValidateErrorHandler;
import com.lego.yxl.core.validator.common.Verifiable;

public class VerifiableBasedValidator {

    public void validate(Object target, ValidateErrorHandler validateErrorHandler) {
        if (target instanceof Verifiable) {
            ((Verifiable) target).validate(validateErrorHandler);
        }
    }
}
