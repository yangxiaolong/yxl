package com.geekhalo.tinyurl.domain;

import com.lego.yxl.core.command.CommandRepository;

public interface TinyUrlCommandRepository extends CommandRepository<TinyUrl, Long> {
    void incrAccessCount(Long id, Integer incrCount);
}
