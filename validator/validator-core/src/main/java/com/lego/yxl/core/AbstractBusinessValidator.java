package com.lego.yxl.core;

public abstract class AbstractBusinessValidator<CXT>
    extends FixTypeValidator<CXT>
    implements BusinessValidator<CXT>{
    public AbstractBusinessValidator(Class<CXT> type) {
        super(type);
    }

    @Override
    public boolean support(CXT type) {
        return type != null && getType().isInstance(type);
    }
}
