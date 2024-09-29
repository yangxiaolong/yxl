package com.lego.yxl.feign.core;


public interface RpcExceptionResolver {
    Exception resolve(String methodKey, int status, String remoteAppName, RpcErrorResult errorResult);
}
