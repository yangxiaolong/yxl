package com.lego.yxl.enums;

public interface SelfDescribedEnum {
    default String getName() {
        return name();
    }

    String name();

    String getDescription();
}
