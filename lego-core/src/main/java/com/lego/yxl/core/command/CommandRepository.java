package com.lego.yxl.core.command;

import com.lego.yxl.core.command.support.AggRoot;

import java.util.Optional;

public interface CommandRepository<E extends AggRoot<ID>, ID>{

    E sync(E entity);

    Optional<E> findById(ID id);
}
