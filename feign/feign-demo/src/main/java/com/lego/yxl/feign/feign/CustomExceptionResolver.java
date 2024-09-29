package com.lego.yxl.feign.feign;

import com.lego.yxl.feign.core.RpcErrorResult;
import com.lego.yxl.feign.core.RpcExceptionResolver;
import org.springframework.stereotype.Component;


@Component
public class CustomExceptionResolver implements RpcExceptionResolver {
    @Override
    public Exception resolve(String methodKey, int status, String remoteAppName, RpcErrorResult errorResult) {
        if (errorResult.getCode() == CustomException.CODE){
            throw new CustomException();
        }
        return null;
    }
}
