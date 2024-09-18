package com.lego.yxl.controller;

import com.lego.yxl.core.lazyloadproxyfactory.LazyLoadProxyFactory;
import com.lego.yxl.context.CreateOrderCmd;
import com.lego.yxl.context.CreateOrderContext;
import com.lego.yxl.context.CreateOrderContextV1;
import com.lego.yxl.context.CreateOrderContextV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangxiaolong
 * @create 2024/9/18
 */
@RestController
@RequestMapping("/loader")
@Slf4j
public class LoaderController {

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @GetMapping("/test1")
    public void test1() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setUserId(1L);
        cmd.setProductId(2L);
        cmd.setCount(2);

        CreateOrderContextV1 context = new CreateOrderContextV1();
        context.setCmd(cmd);
        checkOrderContext(context);
    }

    @GetMapping("/test2")
    public void test2() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setUserId(1L);
        cmd.setProductId(2L);
        cmd.setCount(2);

        CreateOrderContextV2 context = new CreateOrderContextV2();
        context.setCmd(cmd);
        checkOrderContext(context);
    }

    private void checkOrderContext(CreateOrderContext context) {
        CreateOrderContext proxyContext = this.lazyLoadProxyFactory.createProxyFor(context);

        Assertions.assertNotNull(proxyContext);
        log.info("Get Command");
        Assertions.assertNotNull(proxyContext.getCmd());

        log.info("Get Price");
        Assertions.assertNotNull(proxyContext.getPrice());
        // second get price
        Assertions.assertNotNull(proxyContext.getPrice());

        log.info("Get Stock");
        Assertions.assertNotNull(proxyContext.getStock());

        log.info("Get Default Address");
        Assertions.assertNotNull(proxyContext.getDefAddress());

        log.info("Get Product");
        Assertions.assertNotNull(proxyContext.getProduct());

        log.info("Get User");
        Assertions.assertNotNull(proxyContext.getUser());
    }

}
