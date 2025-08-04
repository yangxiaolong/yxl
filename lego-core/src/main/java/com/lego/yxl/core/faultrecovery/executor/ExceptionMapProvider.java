package com.lego.yxl.core.faultrecovery.executor;

import java.util.Map;

public interface ExceptionMapProvider {
    Map<Class<? extends Throwable>, Boolean> get();
}
