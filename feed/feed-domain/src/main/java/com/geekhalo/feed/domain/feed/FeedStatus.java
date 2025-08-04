package com.geekhalo.feed.domain.feed;

import com.lego.yxl.core.enums.CommonEnum;

public enum FeedStatus implements CommonEnum {
    ENABLE(1, "启用"),
    DISABLE(0, "禁用");
    private final int code;
    private final String descr;

    FeedStatus(int code, String descr){
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
