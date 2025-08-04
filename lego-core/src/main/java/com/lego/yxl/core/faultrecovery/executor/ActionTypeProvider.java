package com.lego.yxl.core.faultrecovery.executor;

import com.lego.yxl.core.faultrecovery.smart.ActionType;

public interface ActionTypeProvider {
    ActionType get();
}
