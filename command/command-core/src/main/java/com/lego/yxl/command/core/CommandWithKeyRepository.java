package com.lego.yxl.command.core;

import com.lego.yxl.agg.AggRoot;

import java.util.Optional;

public interface CommandWithKeyRepository<E extends AggRoot<ID>, ID, KEY>
    extends CommandRepository<E, ID>{
    Optional<E> findByKey(KEY key);
}
