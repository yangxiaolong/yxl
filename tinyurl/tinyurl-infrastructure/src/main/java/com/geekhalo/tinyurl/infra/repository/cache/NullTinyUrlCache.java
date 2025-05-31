package com.geekhalo.tinyurl.infra.repository.cache;

import com.geekhalo.tinyurl.domain.TinyUrl;

import java.util.Optional;

public class NullTinyUrlCache implements TinyUrlCache{

    @Override
    public Optional<TinyUrl> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void put(TinyUrl tinyUrl) {

    }

    @Override
    public void incrAccessCount(Long id, int times) {

    }

    @Override
    public void remove(Long id) {

    }
}
