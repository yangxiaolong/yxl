package com.lego.yxl.core.singlequery.annotation;

public enum MaxResultCheckStrategy {
    LOG, // 打印日志
    ERROR, // 抛出异常
    SET_LIMIT // 设置最大值
}
