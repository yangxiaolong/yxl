package com.lego.yxl.core.validator;

public abstract class AbstractRuleValidator<AGG>
    extends FixTypeValidator<AGG>
    implements RuleValidator<AGG> {

    public AbstractRuleValidator(Class<AGG> type) {
        super(type);
    }
}
