package com.lego.yxl.faultrecovery.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public class RetryService1 {
    private int count = 0;

    private int retryCount = 0;
    private int fallbackCount = 0;
    private int recoverCount = 0;

    public void clean() {
        this.retryCount = 0;
        this.fallbackCount = 0;
        this.recoverCount = 0;
    }

    @Retryable(backoff = @Backoff(value = 5))
    public Long retry(Long input) {
        this.retryCount++;
        return doSomething(input);
    }

    @Retryable(maxAttempts = 1)
    public Long fallback(Long input) {
        this.fallbackCount++;
        return doSomething(input);
    }

    @Recover
    public Long recover(Exception e, Long input) {
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
