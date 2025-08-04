package com.lego.yxl.idempotent.controller;

import com.lego.yxl.idempotent.service.BaseIdempotentService;
import com.lego.yxl.idempotent.service.RedisBasedIdempotentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis/idempotent")
@Slf4j
public class RedisIdempotentController extends IdempotentController {

    @Autowired
    private RedisBasedIdempotentService idempotentService;

    @Override
    BaseIdempotentService getIdempotentService() {
        return idempotentService;
    }

}
