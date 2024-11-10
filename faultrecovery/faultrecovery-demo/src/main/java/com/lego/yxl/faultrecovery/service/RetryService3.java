package com.lego.yxl.faultrecovery.service;

import com.lego.yxl.faultrecovery.core.annotation.SmartFault;
import com.lego.yxl.faultrecovery.core.annotation.Action;
import com.lego.yxl.faultrecovery.core.smart.ActionType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public class RetryService3 {
    private int count = 0;

    private int retryCount = 0;
    private int fallbackCount = 0;
    private int recoverCount = 0;

    public void clean() {
        this.retryCount = 0;
        this.fallbackCount = 0;
        this.recoverCount = 0;
    }

    @Action(type = ActionType.COMMAND)
    @SmartFault(recover = "recover")
    public Long retry(Long input) throws Throwable {
        this.retryCount++;
        return doSomething(input);
    }

    @Action(type = ActionType.QUERY)
    @SmartFault(recover = "recover")
    public Long fallback(Long input) throws Throwable {
        this.fallbackCount++;
        return doSomething(input);
    }

    @Recover
    public Long recover(Throwable e, Long input) {
        this.recoverCount++;
        log.info("recover-{}", input);
        return input;
    }

    private Long doSomething(Long input) {
        if (count++ % 2 == 0) {
            log.info("Error-{}", input);
            throw new RuntimeException();
        }
        log.info("Success-{}", input);
        return input;
    }
}
