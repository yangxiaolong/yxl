package com.lego.yxl.command.core;

import com.lego.yxl.agg.AggRoot;

import java.util.Optional;

public interface CommandRepository<E extends AggRoot<ID>, ID>{

    E sync(E entity);

    Optional<E> findById(ID id);
}
