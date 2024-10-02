package com.lego.yxl.core;

import com.lego.yxl.AggRoot;
import com.lego.yxl.core.common.ValidateErrorHandler;
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
