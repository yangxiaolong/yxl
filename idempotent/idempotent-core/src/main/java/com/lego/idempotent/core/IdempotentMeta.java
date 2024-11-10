package com.lego.idempotent.core;

import com.lego.idempotent.annotation.IdempotentHandleType;

public interface IdempotentMeta {
    String executorFactory();

    int group();

    String[] paramNames();

    String keyEl();

    IdempotentHandleType handleType();

    Class returnType();

}
