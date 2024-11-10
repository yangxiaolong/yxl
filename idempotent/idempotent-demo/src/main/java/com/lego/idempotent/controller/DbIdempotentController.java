package com.lego.idempotent.controller;

import com.lego.idempotent.service.BaseIdempotentService;
import com.lego.idempotent.service.DBBasedIdempotentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/db/idempotent")
@Slf4j
public class DbIdempotentController extends IdempotentController {

    @Autowired
    DBBasedIdempotentService idempotentService;

    @Override
    BaseIdempotentService getIdempotentService() {
        return idempotentService;
    }

}
