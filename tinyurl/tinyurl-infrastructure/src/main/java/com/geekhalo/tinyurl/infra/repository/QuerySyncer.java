package com.geekhalo.tinyurl.infra.repository;

import com.geekhalo.tinyurl.domain.AbstractTinyUrlEvent;
import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class QuerySyncer {
    @Autowired
    private TinyUrlQueryRepositoryImpl queryRepository;

    @EventListener
    public void handleEvent(AbstractTinyUrlEvent event){
        TinyUrl source = event.getSource();
        if (source.isEnableCache()) {
            this.queryRepository.clean(event.getSource().getId());
        }
    }

    @EventListener
    public void handleEvent(TinyUrlCreatedEvent event){
        TinyUrl source = event.getSource();
        if (source.isEnableCacheSync()) {
            this.queryRepository.saveToCache(source);
        }
    }
}
