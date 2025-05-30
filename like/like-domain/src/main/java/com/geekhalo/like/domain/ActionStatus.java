package com.geekhalo.like.domain;

import com.lego.yxl.enums.CommonEnum;

public enum ActionStatus implements CommonEnum {
    VALID(1, "有效"),
    INVALID(0, "无效");

    private final int code;
    private final String descr;

    ActionStatus(int code, String descr) {
        this.code = code;
        this.descr = descr;
    }


    @Override
    public String getDescription() {
        return descr;
    }

    @Override
    public int getCode() {
        return code;
    }
}
