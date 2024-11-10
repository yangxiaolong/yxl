package com.lego.yxl.faultrecovery.core.executor;

import com.lego.yxl.faultrecovery.core.smart.ActionType;

public interface ActionTypeProvider {
    ActionType get();
}
