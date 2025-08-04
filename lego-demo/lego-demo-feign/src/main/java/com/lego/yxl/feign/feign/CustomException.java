package com.lego.yxl.feign.feign;

import com.lego.yxl.core.feign.CodeBasedException;

public class CustomException extends RuntimeException implements CodeBasedException {
    public static final int CODE = 550;

    @Override
    public int getErrorCode() {
        return CODE;
    }

    @Override
    public String getErrorMsg() {
        return "自定义异常";
    }
}
