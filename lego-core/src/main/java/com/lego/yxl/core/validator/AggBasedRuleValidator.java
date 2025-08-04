package com.lego.yxl.core.validator;

import com.lego.yxl.core.command.support.AggRoot;
import com.lego.yxl.core.validator.common.ValidateErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class AggBasedRuleValidator<A> implements RuleValidator<A> {

    @Override
    public boolean support(A a) {
        return a instanceof AggRoot;
    }

    @Override
    public void validate(A a, ValidateErrorHandler validateErrorHandler) {
        ((AggRoot) a).validate();
    }

}
