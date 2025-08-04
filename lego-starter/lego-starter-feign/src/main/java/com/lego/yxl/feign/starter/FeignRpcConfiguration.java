package com.lego.yxl.feign.starter;

import com.lego.yxl.core.feign.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FeignRpcConfiguration {
    @Bean
    public RpcRequestInterceptor rpcRequestInterceptor() {
        return new RpcRequestInterceptor();
    }

    @Bean
    public RpcHandlerExceptionResolver rpcExceptionHandler() {
        return new RpcHandlerExceptionResolver();
    }

    @Bean
    public RpcErrorDecoder rpcErrorDecoder(RpcExceptionResolvers resolvers) {
        return new RpcErrorDecoder(resolvers);
    }

    @Bean
    public RpcExceptionResolvers rpcExceptionResolvers(List<RpcExceptionResolver> exceptionResolvers) {
        return new RpcExceptionResolvers(exceptionResolvers);
    }

    @Bean
    public SimpleRpcExceptionResolver simpleRpcExceptionResolver() {
        return new SimpleRpcExceptionResolver();
    }

}
