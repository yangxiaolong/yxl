package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.OrderField;

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
