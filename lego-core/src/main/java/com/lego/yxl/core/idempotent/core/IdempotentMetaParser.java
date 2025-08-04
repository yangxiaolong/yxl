package com.lego.yxl.core.idempotent.core;

import java.lang.reflect.Method;

public interface IdempotentMetaParser {
    IdempotentMeta parse(Method method);
}
