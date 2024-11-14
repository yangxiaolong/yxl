package com.lego.yxl.faultrecovery.controller;

import com.lego.yxl.faultrecovery.service.RetryService1;
import com.lego.yxl.faultrecovery.service.RetryService2;
import com.lego.yxl.faultrecovery.service.RetryService3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faultrecovery")
@Slf4j
public class FaultrecoveryController {

    @Autowired
    RetryService1 retryService1;
    @Autowired
    RetryService2 retryService2;
    @Autowired
    RetryService3 retryService3;

    @GetMapping("/retry")
    public void retry() {
        retryService1.clean();

        for (int i = 0; i < 100; i++) {
            retryService1.retry((long) i);
        }

        Assertions.assertTrue(retryService1.getRetryCount() > 0);
        Assertions.assertEquals(0, retryService1.getRecoverCount());
        Assertions.assertEquals(0, retryService1.getFallbackCount());
    }

    @GetMapping("/fallback")
    public void fallback() {
        retryService1.clean();

        for (int i = 0; i < 100; i++) {
            retryService1.fallback((long) i);
        }

        Assertions.assertEquals(0, retryService1.getRetryCount());
        Assertions.assertTrue(retryService1.getRecoverCount() > 0);
        Assertions.assertTrue(retryService1.getFallbackCount() > 0);
    }

    @GetMapping("/retry2")
    public void retry2() throws Throwable {
        retryService2.clean();

        for (int i = 0; i < 100; i++) {
            retryService2.retry((long) i);
        }
        Assertions.assertTrue(retryService2.getRetryCount() > 0);
        Assertions.assertEquals(0, retryService2.getRecoverCount());
        Assertions.assertEquals(0, retryService2.getFallbackCount());
    }

    @GetMapping("/fallback2")
    public void fallback2() throws Throwable {
        retryService2.clean();

        for (int i = 0; i < 100; i++) {
            retryService2.fallback((long) i);
        }

        Assertions.assertEquals(0, retryService2.getRetryCount());
        Assertions.assertTrue(retryService2.getRecoverCount() > 0);
        Assertions.assertTrue(retryService2.getFallbackCount() > 0);
    }

    @GetMapping("/retry3")
    public void retry3() throws Throwable {
        retryService3.clean();

        for (int i = 0; i < 100; i++) {
            retryService3.retry((long) i);
        }

        Assertions.assertTrue(retryService3.getRetryCount() > 0);
        Assertions.assertEquals(0, retryService3.getRecoverCount());
        Assertions.assertEquals(0, retryService3.getFallbackCount());
    }

    @GetMapping("/fallback3")
    public void fallback3() throws Throwable {
        retryService3.clean();

        for (int i = 0; i < 100; i++) {
            retryService3.fallback((long) i);
        }

        Assertions.assertEquals(0, retryService3.getRetryCount());
        Assertions.assertTrue(retryService3.getRecoverCount() > 0);
        Assertions.assertTrue(retryService3.getFallbackCount() > 0);
    }

}
