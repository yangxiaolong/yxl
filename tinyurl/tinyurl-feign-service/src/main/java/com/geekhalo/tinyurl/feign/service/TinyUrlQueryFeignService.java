package com.geekhalo.tinyurl.feign.service;

import com.geekhalo.tinyurl.api.TinyUrlQueryApi;
import com.geekhalo.tinyurl.app.TinyUrlQueryApplicationService;
import com.geekhalo.tinyurl.domain.TinyUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(TinyUrlQueryApi.PATH)
@Validated
public class TinyUrlQueryFeignService implements TinyUrlQueryApi {

    @Autowired
    private TinyUrlQueryApplicationService queryApplicationService;

    @GetMapping("queryByCode/{code}")
    @Override
    public String queryByCode(@PathVariable String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.findByCode(code);
        return tinyUrlOptional
                .filter(TinyUrl::canAccess)
                .map(TinyUrl::getUrl)
                .orElse(null);
    }

    @GetMapping("accessByCode/{code}")
    @Override
    public String accessByCode(@PathVariable String code) {
        Optional<TinyUrl> tinyUrlOptional = this.queryApplicationService.accessByCode(code);
        return tinyUrlOptional
                .filter(TinyUrl::canAccess)
                .map(TinyUrl::getUrl)
                .orElse(null);
    }
}
