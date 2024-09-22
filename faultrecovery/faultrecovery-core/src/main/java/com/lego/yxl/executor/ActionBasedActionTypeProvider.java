package com.lego.yxl.executor;

import com.lego.yxl.smart.ActionContext;
import com.lego.yxl.smart.ActionType;
import org.springframework.stereotype.Component;

@Component
public class ActionBasedActionTypeProvider implements ActionTypeProvider {
    @Override
    public ActionType get() {
        return ActionContext.get();
    }
}
