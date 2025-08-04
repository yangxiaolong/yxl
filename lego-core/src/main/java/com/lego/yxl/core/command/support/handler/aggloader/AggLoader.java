package com.lego.yxl.core.command.support.handler.aggloader;

import java.util.Optional;

public interface AggLoader<CMD, AGG> {
    Optional<AGG> load(CMD cmd);
}
