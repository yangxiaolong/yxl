package com.lego.yxl.command.core;

@NoCommandService
public interface CommandService<CMD extends Command, RESULT> {
    String METHOD_NAME = "execute";
    RESULT execute(CMD cmd);
}
