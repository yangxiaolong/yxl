package com.lego.yxl;

import com.lego.yxl.common.ValidateErrorHandler;
import com.lego.yxl.common.Verifiable;
import org.springframework.stereotype.Component;

@Component
public class VerifiableBasedParamValidator implements ParamValidator<Object> {
    @Override
    public boolean support(Object verifiable) {
        return verifiable instanceof Verifiable;
    }

    @Override
    public void validate(Object verifiable, ValidateErrorHandler validateErrorHandler) {
        ((Verifiable) verifiable).validate(validateErrorHandler);
    }
}
