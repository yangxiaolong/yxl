package com.lego.yxl.core.enums;

public interface SelfDescribedEnum {
    default String getName() {
        return name();
    }

    String name();

    String getDescription();
}
