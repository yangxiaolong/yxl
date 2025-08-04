package com.lego.yxl.core.command.support.handler.aggsyncer;

import com.lego.yxl.core.command.support.AggRoot;

public interface SmartAggSyncer <AGG extends AggRoot> extends AggSyncer<AGG> {
    boolean apply(Class aggClass);
}
