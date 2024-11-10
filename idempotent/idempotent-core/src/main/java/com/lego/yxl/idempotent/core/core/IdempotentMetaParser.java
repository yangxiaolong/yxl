package com.lego.yxl.idempotent.core.core;

import java.lang.reflect.Method;

public interface IdempotentMetaParser {
    IdempotentMeta parse(Method method);
}
