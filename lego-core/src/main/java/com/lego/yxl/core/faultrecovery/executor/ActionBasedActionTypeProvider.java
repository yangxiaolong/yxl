package com.lego.yxl.core.faultrecovery.executor;

import com.lego.yxl.core.faultrecovery.smart.ActionContext;
import com.lego.yxl.core.faultrecovery.smart.ActionType;
import org.springframework.stereotype.Component;

@Component
public class ActionBasedActionTypeProvider implements ActionTypeProvider {
    @Override
    public ActionType get() {
        return ActionContext.get();
    }
}
