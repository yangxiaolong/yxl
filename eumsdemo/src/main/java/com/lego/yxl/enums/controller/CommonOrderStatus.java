package com.lego.yxl.enums.controller;

import com.lego.yxl.enums.CommonEnum;

/**
 * @auther yangxiaolong
 * @create 2025/5/28
 */
public enum CommonOrderStatus implements CommonEnum {
    CREATED(1, "待支付"),
    TIMEOUT_CANCELLED(2, "超时取消"),
    MANUAL_CANCELLED(5, "手工取消"),
    PAID(3, "支付成功"),
    FINISHED(4, "已完成");
    private final int code;
    private final String description;

    CommonOrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}