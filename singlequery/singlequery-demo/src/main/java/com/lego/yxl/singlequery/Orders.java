package com.lego.yxl.singlequery;

import com.lego.yxl.core.OrderField;

public enum Orders implements OrderField {
    ID("id");

    Orders(String name) {
        this.name = name;
    }

    private final String name;
    @Override
    public String getColumnName() {
        return name;
    }
}
