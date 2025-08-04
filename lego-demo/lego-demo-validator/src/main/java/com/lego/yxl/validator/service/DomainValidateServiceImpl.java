package com.lego.yxl.validator.service;

import com.lego.yxl.core.validator.ValidateService;
import com.lego.yxl.validator.context.CreateOrderCmd;
import com.lego.yxl.validator.context.CreateOrderContextV2;
import com.lego.yxl.core.loader.lazyloadproxyfactory.LazyLoadProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DomainValidateServiceImpl implements DomainValidateService {
    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ValidateService validateService;


    @Override
    public void createOrder(CreateOrderContext context) {
        validateService.validateBusiness(context);
    }

    @Override
    public void createOrder(CreateOrderCmd cmd) {
        CreateOrderContextV2 context = new CreateOrderContextV2();
        context.setCmd(cmd);
        CreateOrderContextV2 contextProxy = this.lazyLoadProxyFactory.createProxyFor(context);
        this.validateService.validateBusiness(contextProxy);
        log.info("context is {}", contextProxy);
    }
}
