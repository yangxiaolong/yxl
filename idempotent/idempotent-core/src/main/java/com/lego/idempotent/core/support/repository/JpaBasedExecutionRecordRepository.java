package com.lego.idempotent.core.support.repository;

import com.lego.idempotent.core.support.ExecutionRecordRepository;
import com.lego.idempotent.core.support.ExecutionRecord;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedExecutionRecordRepository extends JpaRepository<ExecutionRecord, Long>, ExecutionRecordRepository {

    ExecutionRecord getByTypeAndUniqueKey(int type, String key);

    default void update(ExecutionRecord executionRecord) {
        save(executionRecord);
    }

    default ExecutionRecord getOrCreate(int type, String key) {
        ExecutionRecord executionRecordInDB = getByTypeAndUniqueKey(type, key);
        if (executionRecordInDB != null) {
            // 已经有记录，如果是 none 状态，说明在处理中
            if (executionRecordInDB.isNone()) {
                executionRecordInDB.ing();
            }
            return executionRecordInDB;
        }


        ExecutionRecord executionRecord = ExecutionRecord.apply(type, key);
        executionRecord.init();
        try {
            // 保存成功，没有冲突，作为返回值
            this.save(executionRecord);
        } catch (DataIntegrityViolationException exception) {
            // 保存失败，出现唯一键异常，重新读取数据
            executionRecord = getByTypeAndUniqueKey(type, key);
            if (executionRecord.isNone()) {
                executionRecord.ing();
            }
        }
        return executionRecord;
    }
}
