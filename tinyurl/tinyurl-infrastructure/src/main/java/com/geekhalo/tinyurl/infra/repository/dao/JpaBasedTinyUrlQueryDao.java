package com.geekhalo.tinyurl.infra.repository.dao;

import com.geekhalo.tinyurl.domain.TinyUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedTinyUrlQueryDao
        extends JpaRepository<TinyUrl, Long> {
}
