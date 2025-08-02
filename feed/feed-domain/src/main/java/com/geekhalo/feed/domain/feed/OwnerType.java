package com.geekhalo.feed.domain.feed;

import com.lego.yxl.enums.CommonEnum;

public enum OwnerType implements CommonEnum {
    USER(1, "User"),
    COMPANY(2, "公司"),
    TEST(0, "Test");
    private final int code;
    private final String descr;

    OwnerType(int code, String descr){
        this.code = code;
        this.descr = descr;
    }
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.descr;
    }
}
