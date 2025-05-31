package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.TinyUrl;

import java.util.Optional;

public interface TinyUrlQueryApplicationService {

    Optional<TinyUrl> findById(Long id);

    Optional<TinyUrl> findByCode(String code);

    Optional<TinyUrl> accessById(Long id);

    Optional<TinyUrl> accessByCode(String code);
}
