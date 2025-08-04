package com.lego.yxl.core.command.support.handler;

public interface AggCommandHandler<
        CMD,
        RESULT> extends CommandHandler<CMD, RESULT>{
    @Override
    RESULT handle(CMD cmd);
}
