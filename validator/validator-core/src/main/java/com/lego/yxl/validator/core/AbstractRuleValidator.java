package com.lego.yxl.validator.core;

public abstract class AbstractRuleValidator<AGG>
    extends FixTypeValidator<AGG>
    implements RuleValidator<AGG> {

    public AbstractRuleValidator(Class<AGG> type) {
        super(type);
    }
}
