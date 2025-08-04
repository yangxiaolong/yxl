package com.lego.yxl.core.command;

import com.lego.yxl.core.command.support.AggRoot;

import java.util.Optional;

public interface CommandWithKeyRepository<E extends AggRoot<ID>, ID, KEY>
    extends CommandRepository<E, ID>{
    Optional<E> findByKey(KEY key);
}
