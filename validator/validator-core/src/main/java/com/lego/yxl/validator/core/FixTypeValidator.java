package com.lego.yxl.validator.core;


import lombok.Getter;

@Getter
abstract class FixTypeValidator<A> implements BaseValidator<A> {
    private final Class<A> type;

    public FixTypeValidator(Class<A> type) {
        this.type = type;
    }

    @Override
    public boolean support(A a) {
        return a != null && a.getClass().equals(this.type);
    }
}
