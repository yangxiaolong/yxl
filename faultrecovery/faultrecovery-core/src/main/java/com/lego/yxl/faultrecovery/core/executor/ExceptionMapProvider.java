package com.lego.yxl.faultrecovery.core.executor;

import java.util.Map;

public interface ExceptionMapProvider {
    Map<Class<? extends Throwable>, Boolean> get();
}
