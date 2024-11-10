package com.lego.yxl.command.core.support.handler;

public interface AggCommandHandler<
        CMD,
        RESULT> extends CommandHandler<CMD, RESULT>{
    @Override
    RESULT handle(CMD cmd);
}
