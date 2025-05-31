package com.geekhalo.tinyurl.infra.repository;

import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JpaBasedTinyUrlCommandRepository
        extends TinyUrlCommandRepository,
        JpaRepository<TinyUrl, Long> {

    @Override
    default TinyUrl sync(TinyUrl tinyUrl){
        return save(tinyUrl);
    }

    @Override
    @Query("update TinyUrl set accessCount =  accessCount + ?2 where id = ?1")
    @Modifying
    @Transactional(readOnly = false)
    void incrAccessCount(Long id, Integer incrCount);
}
