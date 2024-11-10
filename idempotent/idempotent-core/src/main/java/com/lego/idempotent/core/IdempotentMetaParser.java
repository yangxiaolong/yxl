package com.lego.idempotent.core;

import java.lang.reflect.Method;

public interface IdempotentMetaParser {
    IdempotentMeta parse(Method method);
}
