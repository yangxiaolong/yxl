package com.lego.yxl.core.feign;

import lombok.EqualsAndHashCode;
import lombok.Value;


@EqualsAndHashCode(callSuper = true)
@Value
public class RpcException extends RuntimeException{
    private final String methodKey;
    private final int httpStatus;
    private final String remoteAppName;
    private final int errorCode;
    private final String errorMsg;

    public RpcException(String methodKey, int httpStatus, String remoteAppName, RpcErrorResult errorResult){
        this.methodKey = methodKey;
        this.httpStatus = httpStatus;
        this.remoteAppName = remoteAppName;
        if (errorResult != null){
            this.errorCode = errorResult.getCode();
            this.errorMsg = errorResult.getMsg();
        }else {
            this.errorCode = -1;
            this.errorMsg = "未知错误";
        }
    }
}
