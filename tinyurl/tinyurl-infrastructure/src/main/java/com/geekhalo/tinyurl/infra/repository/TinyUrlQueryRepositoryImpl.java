package com.geekhalo.tinyurl.infra.repository;

import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;
import com.geekhalo.tinyurl.infra.repository.cache.TinyUrlCache;
import com.geekhalo.tinyurl.infra.repository.dao.JpaBasedTinyUrlQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TinyUrlQueryRepositoryImpl implements TinyUrlQueryRepository {
    @Autowired
    private JpaBasedTinyUrlQueryDao queryDao;

    @Autowired
    private TinyUrlCache queryCache;

    @Override
    public Optional<TinyUrl> findById(Long id) {
        Optional<TinyUrl> optionalTinyUrl = queryCache.findById(id);
        if (optionalTinyUrl.isPresent()){
            return optionalTinyUrl;
        }else {
            Optional<TinyUrl> tinyUrlFromDao = this.queryDao.findById(id);
            if (tinyUrlFromDao.isPresent()){
                TinyUrl tinyUrl = tinyUrlFromDao.get();
                this.queryCache.put(tinyUrl);
                return tinyUrlFromDao;
            }
            return Optional.empty();
        }
    }

    @Override
    public void incrAccessCount(Long id, Integer incrCount) {
        queryCache.incrAccessCount(id, incrCount);
    }

    public void clean(Long id) {
        this.queryCache.remove(id);
    }

    public void saveToCache(TinyUrl source) {
        this.queryCache.put(source);
    }
}
