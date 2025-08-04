package com.lego.yxl.core.idempotent.core;

import com.lego.yxl.core.idempotent.annotation.IdempotentHandleType;

public interface IdempotentMeta {
    String executorFactory();

    int group();

    String[] paramNames();

    String keyEl();

    IdempotentHandleType handleType();

    Class returnType();

}
