package com.lego.yxl.core.idempotent.annotation;

public enum IdempotentHandleType {
    ERROR, // 抛出异常
    RESULT; // 返回执行结果
}
