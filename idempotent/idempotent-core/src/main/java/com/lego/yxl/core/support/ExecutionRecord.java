package com.lego.yxl.core.support;

import com.google.common.base.Preconditions;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@NoArgsConstructor
@Data
@Table(name = "idempotent_execution_record")
@Entity
public class ExecutionRecord {
    private static final Integer STATUS_NONE = 0;
    private static final Integer STATUS_ING = 1;
    private static final Integer STATUS_DONE_SUCCESS = 2;
    private static final Integer STATUS_DONE_ERROR = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer type;
    private String uniqueKey;

    private String result;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public boolean isIng() {
        return STATUS_ING.equals(getStatus());
    }

    public boolean isDone() {
        return isSuccess() || isError();
    }

    public boolean isSuccess() {
        return STATUS_DONE_SUCCESS.equals(getStatus());
    }

    public boolean isError() {
        return STATUS_DONE_ERROR.equals(getStatus());
    }

    public void success(String result) {
        setResult(result);
        setStatus(STATUS_DONE_SUCCESS);
        setUpdateDate(new Date());
    }

    public void error(String error) {
        setResult(error);
        setStatus(STATUS_DONE_ERROR);
        setUpdateDate(new Date());
    }

    public static ExecutionRecord apply(Integer group, String key) {
        Preconditions.checkArgument(group != null);
        Preconditions.checkArgument(StringUtils.isNoneBlank(key));

        ExecutionRecord executionRecord = new ExecutionRecord();
        executionRecord.setType(group);
        executionRecord.setUniqueKey(key);
        return executionRecord;
    }

    public void init() {
        setStatus(STATUS_NONE);
        setCreateDate(new Date());
    }

    public void ing() {
        setStatus(STATUS_ING);
    }

    public boolean isNone() {
        return STATUS_NONE.equals(getStatus());
    }

}
