package com.geekhalo.tinyurl.infra.repository.cache;

import com.geekhalo.tinyurl.domain.TinyUrl;

import java.util.Optional;

public interface TinyUrlCache {
    Optional<TinyUrl> findById(Long id);

    void put(TinyUrl tinyUrl);

    void incrAccessCount(Long id, int times);

    void remove(Long id);
}
