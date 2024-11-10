package com.lego.yxl.command.core.support.handler.aggloader;

import java.util.Optional;

public interface AggLoader<CMD, AGG> {
    Optional<AGG> load(CMD cmd);
}
