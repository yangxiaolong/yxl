package com.geekhalo.tinyurl.feign.service;

import com.geekhalo.tinyurl.api.*;
import com.geekhalo.tinyurl.app.TinyUrlCommandApplicationService;
import com.geekhalo.tinyurl.domain.*;
import com.geekhalo.tinyurl.domain.codec.NumberCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TinyUrlCommandApi.PATH)
@Validated
public class TinyUrlCommandFeignService implements TinyUrlCommandApi {
    @Autowired
    private TinyUrlCommandApplicationService commandApplicationService;
    @Autowired
    private NumberCodec numberCodec;

    @Override
    @PostMapping("_createCommon")
    public CreateTinyUrlResult createCommonTinyUrl(@RequestBody CreateCommonRequest request) {
        CreateTinyUrlCommand command = new CreateTinyUrlCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        TinyUrl tinyUrl = this.commandApplicationService.createTinyUrl(command);
        String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResult(code);
    }

    @Override
    @PostMapping("_createExpire")
    public CreateTinyUrlResult createExpire(@RequestBody CreateExpireRequest request) {
        CreateExpireTimeTinyUrlCommand command = new CreateExpireTimeTinyUrlCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        command.setBeginTime(request.getBeginTime());
        command.setExpireTime(request.getExpireTime());
        TinyUrl tinyUrl = this.commandApplicationService.createExpireTimeTinyUrl(command);
        String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResult(code);
    }

    @Override
    @PostMapping("_createLimitAccessCount")
    public CreateTinyUrlResult createLimitAccessCount(@RequestBody CreateLimitAccessCountRequest request) {
        CreateLimitTimeTinyUrlCommand command = new CreateLimitTimeTinyUrlCommand();
        command.setUrl(request.getUrl());
        command.setEnableCache(request.getEnableCache());
        command.setEnableCacheSync(request.getEnableCacheSync());
        command.setMaxCount(request.getMaxCount());
        TinyUrl tinyUrl = this.commandApplicationService.createLimitTimeTinyUrl(command);
        String code = this.numberCodec.encode(tinyUrl.getId());
        return new CreateTinyUrlResult(code);
    }

    @PostMapping("{id}/_disable/")
    @Override
    public void disable(@PathVariable Long id) {
        DisableTinyUrlCommand command = new DisableTinyUrlCommand();
        command.setId(id);
        commandApplicationService.disableTinyUrl(command);
    }
}

