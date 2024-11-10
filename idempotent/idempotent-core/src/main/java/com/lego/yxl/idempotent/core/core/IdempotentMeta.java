package com.lego.yxl.idempotent.core.core;

import com.lego.yxl.idempotent.core.annotation.IdempotentHandleType;

public interface IdempotentMeta {
    String executorFactory();

    int group();

    String[] paramNames();

    String keyEl();

    IdempotentHandleType handleType();

    Class returnType();

}
