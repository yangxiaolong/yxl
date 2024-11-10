package com.lego.yxl.faultrecovery.core.executor;

import com.lego.yxl.faultrecovery.core.smart.ActionContext;
import com.lego.yxl.faultrecovery.core.smart.ActionType;
import org.springframework.stereotype.Component;

@Component
public class ActionBasedActionTypeProvider implements ActionTypeProvider {
    @Override
    public ActionType get() {
        return ActionContext.get();
    }
}
