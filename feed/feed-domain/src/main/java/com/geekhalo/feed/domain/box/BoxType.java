package com.geekhalo.feed.domain.box;

import com.lego.yxl.enums.CommonEnum;

public enum BoxType implements CommonEnum {
    IN_BOX(1,  1000, 1000, "输入"),
    OUT_BOX(2, 1000, 1000, "输出"),
    TEST_BOX(3,  100, 100, "测试");
    private final int code;
    private final int maxCacheLength;
    private final int maxPreRow;
    private final String descr;

    BoxType(int code, int maxCacheLength, int maxPreRow, String descr){
        this.code = code;
        this.maxCacheLength = maxCacheLength;
        this.maxPreRow = maxPreRow;
        this.descr = descr;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    public int getMaxCacheLength() {
        return maxCacheLength;
    }

    public int getMaxPreRow() {
        return maxPreRow;
    }

    @Override
    public String getDescription() {
        return this.descr;
    }
}
