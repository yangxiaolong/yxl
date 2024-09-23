package com.lego.yxl.core;

import java.lang.reflect.Method;

public interface IdempotentMetaParser {
    IdempotentMeta parse(Method method);
}
