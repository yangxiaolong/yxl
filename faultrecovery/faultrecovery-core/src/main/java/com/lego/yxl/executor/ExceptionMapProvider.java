package com.lego.yxl.executor;

import java.util.Map;

public interface ExceptionMapProvider {
    Map<Class<? extends Throwable>, Boolean> get();
}
