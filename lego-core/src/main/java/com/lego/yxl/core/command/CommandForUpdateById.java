package com.lego.yxl.core.command;

public interface CommandForUpdateById<ID> extends CommandForUpdate {
    ID getId();
}
