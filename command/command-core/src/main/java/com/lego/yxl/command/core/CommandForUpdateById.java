package com.lego.yxl.command.core;

public interface CommandForUpdateById<ID> extends CommandForUpdate {
    ID getId();
}
