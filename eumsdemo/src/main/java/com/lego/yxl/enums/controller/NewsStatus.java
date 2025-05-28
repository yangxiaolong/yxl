package com.lego.yxl.enums.controller;

import com.lego.yxl.enums.CommonEnum;

public enum NewsStatus implements CommonEnum {
    DELETE(1, "删除"),
    ONLINE(10, "上线"),
    OFFLINE(20, "下线");
    private final int code;
    private final String desc;

    NewsStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}
