package com.lego.yxl.validator.core;

import com.lego.yxl.agg.AggRoot;
import com.lego.yxl.validator.core.common.ValidateErrorHandler;
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
