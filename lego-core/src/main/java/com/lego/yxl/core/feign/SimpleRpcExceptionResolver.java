package com.lego.yxl.core.feign;


public class SimpleRpcExceptionResolver implements RpcExceptionResolver {
    @Override
    public Exception resolve(String methodKey, int status, String remoteAppName, RpcErrorResult errorResult) {
        return new RpcException(methodKey, status, remoteAppName, errorResult);
    }
}
