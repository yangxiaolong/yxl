package com.lego.yxl.jpatest.valid;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
@Data
public final class Result<T> implements Serializable {

    private boolean success = true;
    private T data = null;

    private String errCode;
    private String errMsg;
}