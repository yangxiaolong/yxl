package com.lego.yxl.feign.feign;

import com.lego.yxl.core.feign.CodeBasedException;

public class TestPostException extends RuntimeException implements CodeBasedException {
    @Override
    public int getErrorCode() {
        return 531;
    }

    @Override
    public String getErrorMsg() {
        return "测试异常";
    }
}
