package com.lego.yxl.command.core.support.handler.aggsyncer;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AggSyncerNotFoundException extends RuntimeException{
    private final Class aggClass;
}
