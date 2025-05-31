package com.geekhalo.tinyurl.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TinyUrlCommandApi {
    String PATH = "tinyurl/command";

    @PostMapping("_createCommon")
    CreateTinyUrlResult createCommonTinyUrl(@RequestBody CreateCommonRequest request);

    @PostMapping("_createExpire")
    CreateTinyUrlResult createExpire(@RequestBody CreateExpireRequest request);

    @PostMapping("_createLimitAccessCount")
    CreateTinyUrlResult createLimitAccessCount(@RequestBody CreateLimitAccessCountRequest request);

    @PostMapping("disable/{id}")
    void disable(@PathVariable Long id);
}
