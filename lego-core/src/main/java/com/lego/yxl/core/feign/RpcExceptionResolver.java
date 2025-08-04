package com.lego.yxl.core.feign;


public interface RpcExceptionResolver {
    Exception resolve(String methodKey, int status, String remoteAppName, RpcErrorResult errorResult);
}
