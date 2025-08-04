package com.lego.yxl.idempotent.controller;

import com.lego.yxl.core.idempotent.common.ConcurrentRequestException;
import com.lego.yxl.core.idempotent.common.RepeatedSubmitException;
import com.lego.yxl.idempotent.service.BaseIdempotentService;
import com.lego.yxl.idempotent.service.IdempotentTestException;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public abstract class IdempotentController {

    abstract BaseIdempotentService getIdempotentService();

    @GetMapping("/putForResult")
    public void putForResult() {
        getIdempotentService().clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，返回值和最终结果符合预期
            Long result = getIdempotentService().putForResult(key, value);
            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, getIdempotentService().getValue(key));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Long result = getIdempotentService().putForResult(key, valueNew);

            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, getIdempotentService().getValue(key));
        }

        getIdempotentService().clean();
    }

    @GetMapping("/putForError")
    public void putForError() {
        getIdempotentService().clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        { // 第一次操作，返回值和最终结果符合预期
            Long result = getIdempotentService().putForError(key, value);
            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, getIdempotentService().getValue(key));
        }

        { // 第二次操作，直接抛出异常，结果与第一次一致
            Assertions.assertThrows(RepeatedSubmitException.class, () -> {
                Long valueNew = RandomUtils.nextLong();
                getIdempotentService().putForError(key, valueNew);
            });
            Assertions.assertEquals(value, getIdempotentService().getValue(key));
        }

        getIdempotentService().clean();
    }

    @GetMapping("/putExceptionForResult")
    public void putExceptionForResult() {
        getIdempotentService().clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，抛出异常
            Assertions.assertThrows(IdempotentTestException.class,
                    () -> getIdempotentService().putExceptionForResult(key, value));
            Assertions.assertEquals(value, getIdempotentService().getValue(key));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Assertions.assertThrows(IdempotentTestException.class,
                    () -> getIdempotentService().putExceptionForResult(key, valueNew));
            Assertions.assertEquals(value, getIdempotentService().getValue(key));
        }

        getIdempotentService().clean();
    }

    @GetMapping("/putExceptionForError")
    public void putExceptionForError() {
        getIdempotentService().clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，抛出异常
            Assertions.assertThrows(IdempotentTestException.class,
                    () -> getIdempotentService().putExceptionForResult(key, value));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Assertions.assertThrows(RepeatedSubmitException.class,
                    () -> getIdempotentService().putExceptionForError(key, valueNew));
        }

        getIdempotentService().clean();
    }

    @GetMapping("/putWaitForResult")
    public void putWaitForResult() {
        getIdempotentService().clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        // 主线程抛出 ConcurrentRequestException
        Assertions.assertThrows(ConcurrentRequestException.class, () ->
                testForConcurrent(baseIdempotentService ->
                        getIdempotentService().putWaitForResult(key, value))
        );

        getIdempotentService().clean();
    }

    @GetMapping("/putWaitForError")
    public void putWaitForError() {
        getIdempotentService().clean();

        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();
        Assertions.assertThrows(ConcurrentRequestException.class, () ->
                testForConcurrent(baseIdempotentService ->
                        getIdempotentService().putWaitForError(key, value))
        );

        getIdempotentService().clean();
    }

    private void testForConcurrent(Consumer<BaseIdempotentService> consumer) throws InterruptedException {
        // 启动一个线程执行任务，模拟并发场景
        Thread thread = new Thread(() -> consumer.accept(getIdempotentService()));
        thread.start();

        // 主线程 sleep 1 秒，与异步线程并行执行任务
        TimeUnit.SECONDS.sleep(1);
        consumer.accept(getIdempotentService());
    }

}
