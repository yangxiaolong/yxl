package com.lego.yxl.core.idempotent.core.support;

import com.lego.yxl.core.idempotent.annotation.IdempotentHandleType;
import com.lego.yxl.core.idempotent.common.ConcurrentRequestException;
import com.lego.yxl.core.idempotent.common.RepeatedSubmitException;
import com.lego.yxl.core.idempotent.core.IdempotentExecutor;
import com.lego.yxl.core.idempotent.core.IdempotentMeta;
import com.lego.yxl.core.idempotent.core.support.repository.ExecutionRecord;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class SimpleIdempotentExecutor implements IdempotentExecutor {
    private final IdempotentMeta meta;
    private final IdempotentKeyParser idempotentKeyParser;
    private final ExecutionResultSerializer serializer;
    private final ExecutionRecordRepository executionRecordRepository;

    protected SimpleIdempotentExecutor(IdempotentMeta meta,
                                       IdempotentKeyParser idempotentKeyParser,
                                       ExecutionResultSerializer serializer,
                                       ExecutionRecordRepository executionRecordRepository) {
        this.meta = meta;
        this.idempotentKeyParser = idempotentKeyParser;
        this.serializer = serializer;
        this.executionRecordRepository = executionRecordRepository;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //** 第一步，解析幂等键。**
        //用方法参数名和 SpEL 表达式，把 `#key` 解析成真实的业务键。
        String key = this.idempotentKeyParser.parse(
                this.meta.paramNames(), invocation.getArguments(), this.meta.keyEl());

        //**第二步，获取或创建执行记录。**
        //注意这里是 `getOrCreate`。如果记录不存在就创建一条，状态是 `NONE`；
        // 如果已存在就直接返回。这个动作在 DB 实现里靠“先查、再插、捕获唯一键冲突再查”来完成，
        // 在 Redis 实现里靠 `setIfAbsent` 来完成。
        ExecutionRecord executionRecord = getOrCreateExecutionRecord(this.meta.group(), key);

        //**第三步，按状态分流。**
        // 3.1如果记录是**已完成**（成功或失败）
        if (executionRecord.isDone()) {
            // 异常中断逻辑
            //handleType == ERROR`，直接抛 `RepeatedSubmitException`
            if (this.meta.handleType() == IdempotentHandleType.ERROR) {
                log.info("In Error Mode, throw RepeatedSubmitException");
                throw new RepeatedSubmitException();
            }

            //`handleType == RESULT`，成功就反序列化上次结果返回，失败就反序列化上次异常抛出。
            // 返回上次结果
            if (executionRecord.isSuccess()) {
                log.info("result is success for {}-{}, return last result {}",
                        this.meta.group(), key, executionRecord.getResult());
                String result = executionRecord.getResult();
                return this.serializer.deserializeResult(result, this.meta.returnType());
            } else {
                log.info("result is error for {}-{}, return exception {}",
                        this.meta.group(), key, executionRecord.getResult());
                String result = executionRecord.getResult();
                throw this.serializer.deserializeException(result);
            }
        }

        //3.2如果记录是**执行中**（`ING`）：说明有并发请求，直接抛 `ConcurrentRequestException`。
        if (executionRecord.isIng()) {
            log.info("Method is Running for key {}-{}", this.meta.group(), key);
            // 进行中，抛出异常
            throw new ConcurrentRequestException();
        }

        //第四步，执行并落档
        //NONE --执行业务--> 成功: DONE_SUCCESS(result=返回值)
        //              --> 异常: DONE_ERROR(result=异常类名)
        try {
            log.info("Method is no Run for key {}-{}", this.meta.group(), key);
            // 执行成功，保存执行结果
            Object result = invocation.proceed();
            String resultJson = this.serializer.serializeResult(result);
            executionRecord.success(resultJson);
            updateExecutionRecord(executionRecord);
            log.info("Method is success for key {}-{}, result is {}", this.meta.group(), key, resultJson);
            return result;
        } catch (Exception e) {
            // 执行异常，保存异常结果
            String errorJson = this.serializer.serializeException(e);
            executionRecord.error(errorJson);
            updateExecutionRecord(executionRecord);
            log.info("Method is error for key {}-{}, result is {}", this.meta.group(), key, errorJson);
            throw e;
        }
    }

    protected void updateExecutionRecord(ExecutionRecord executionRecord) {
        this.executionRecordRepository.update(executionRecord);
    }

    protected ExecutionRecord getOrCreateExecutionRecord(int group, String key) {
        return this.executionRecordRepository.getOrCreate(group, key);
    }

}
