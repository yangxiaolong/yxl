package com.lego.yxl.core;

import com.lego.yxl.annotation.IdempotentHandleType;

public interface IdempotentMeta {
    String executorFactory();

    int group();

    String[] paramNames();

    String keyEl();

    IdempotentHandleType handleType();

    Class returnType();

}
