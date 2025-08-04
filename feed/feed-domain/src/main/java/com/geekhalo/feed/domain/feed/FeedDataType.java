package com.geekhalo.feed.domain.feed;

import com.lego.yxl.core.enums.CommonEnum;

public enum FeedDataType implements CommonEnum {
    TEST(-1, "测试");
    private final int code;
    private final String descr;

    FeedDataType(int code, String descr){
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
