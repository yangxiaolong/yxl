package com.lego.yxl.controller;

import com.lego.yxl.common.ConcurrentRequestException;
import com.lego.yxl.common.RepeatedSubmitException;
import com.lego.yxl.service.BaseIdempotentService;
import com.lego.yxl.service.DBBasedIdempotentService;
import com.lego.yxl.service.IdempotentTestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/db/idempotent")
@Slf4j
public class DbIdempotentController {

    @Autowired
    DBBasedIdempotentService idempotentService;

    @GetMapping("/putForResult")
    public void putForResult() {
        idempotentService.clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，返回值和最终结果符合预期
            Long result = idempotentService.putForResult(key, value);
            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Long result = idempotentService.putForResult(key, valueNew);

            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        idempotentService.clean();
    }

    @GetMapping("/putForError")
    public void putForError() {
        idempotentService.clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        { // 第一次操作，返回值和最终结果符合预期
            Long result = idempotentService.putForError(key, value);
            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        { // 第二次操作，直接抛出异常，结果与第一次一致
            Assertions.assertThrows(RepeatedSubmitException.class, () -> {
                Long valueNew = RandomUtils.nextLong();
                idempotentService.putForError(key, valueNew);
            });
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        idempotentService.clean();
    }

    @GetMapping("/putExceptionForResult")
    public void putExceptionForResult() {
        idempotentService.clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，抛出异常
            Assertions.assertThrows(IdempotentTestException.class,
                    () -> idempotentService.putExceptionForResult(key, value));
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Assertions.assertThrows(IdempotentTestException.class,
                    () -> idempotentService.putExceptionForResult(key, valueNew));
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        idempotentService.clean();
    }

    @GetMapping("/putExceptionForError")
    public void putExceptionForError() {
        idempotentService.clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，抛出异常
            Assertions.assertThrows(IdempotentTestException.class,
                    () -> idempotentService.putExceptionForResult(key, value));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Assertions.assertThrows(RepeatedSubmitException.class,
                    () -> idempotentService.putExceptionForError(key, valueNew));
        }

        idempotentService.clean();
    }

    @GetMapping("/putWaitForResult")
    public void putWaitForResult() {
        idempotentService.clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        // 主线程抛出 ConcurrentRequestException
        Assertions.assertThrows(ConcurrentRequestException.class, () ->
                testForConcurrent(baseIdempotentService ->
                        baseIdempotentService.putWaitForResult(key, value))
        );

        idempotentService.clean();
    }

    @GetMapping("/putWaitForError")
    public void putWaitForError() {
        idempotentService.clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();
        Assertions.assertThrows(ConcurrentRequestException.class, () ->
                testForConcurrent(baseIdempotentService ->
                        baseIdempotentService.putWaitForError(key, value))
        );

        idempotentService.clean();
    }

    private void testForConcurrent(Consumer<BaseIdempotentService> consumer) throws InterruptedException {
        // 启动一个线程执行任务，模拟并发场景
        Thread thread = new Thread(() -> consumer.accept(idempotentService));
        thread.start();

        // 主线程 sleep 1 秒，与异步线程并行执行任务
        TimeUnit.SECONDS.sleep(1);
        consumer.accept(idempotentService);
    }

}
