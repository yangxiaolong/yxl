package com.geekhalo.tinyurl.app.impl;

import com.geekhalo.tinyurl.app.TinyUrlAccessedEvent;
import com.geekhalo.tinyurl.app.TinyUrlQueryApplicationService;
import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;
import com.geekhalo.tinyurl.domain.codec.NumberCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyUrlQueryApplicationServiceImpl implements TinyUrlQueryApplicationService {

    @Autowired
    private TinyUrlQueryRepository tinyUrlQueryRepository;

    @Autowired
    private NumberCodec numberCodec;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public Optional<TinyUrl> findById(Long id) {
        if (id == null){
            return Optional.empty();
        }
        return tinyUrlQueryRepository.findById(id);
    }

    @Override
    public Optional<TinyUrl> findByCode(String code) {
        if (StringUtils.isEmpty(code)){
            return Optional.empty();
        }

        Long number = this.numberCodec.decode(code);
        return this.findById(number);
    }

    @Override
    public Optional<TinyUrl> accessById(Long id) {
        Optional<TinyUrl> tinyUrlOptional = findById(id);

        return tinyUrlOptional.map(tinyUrl -> {
            TinyUrlAccessedEvent event = new TinyUrlAccessedEvent(tinyUrl);
            this.eventPublisher.publishEvent(event);
            tinyUrl.incrAccessCount(1);
            return tinyUrl;
        });

    }

    @Override
    public Optional<TinyUrl> accessByCode(String code) {
        Long id = this.numberCodec.decode(code);
        return accessById(id);
    }
}
