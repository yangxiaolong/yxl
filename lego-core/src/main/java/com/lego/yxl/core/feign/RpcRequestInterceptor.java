package com.lego.yxl.core.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;


public class RpcRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(RpcConstants.RPC_TAG_HEADER, RpcConstants.RPC_TAG_VALUE);
    }
}
