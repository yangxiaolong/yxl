package com.lego.yxl.wide.support;

import lombok.Value;

import java.lang.reflect.Field;

@Value
public class FieldMapper{
    private final Field itemField;
    private final Field wideField;

    public FieldMapper(Field itemField, Field wideField) {
        this.itemField = itemField;
        this.wideField = wideField;
    }
}