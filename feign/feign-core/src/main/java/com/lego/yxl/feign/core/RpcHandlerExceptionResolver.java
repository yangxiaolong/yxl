package com.lego.yxl.feign.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Order(0)
public class RpcHandlerExceptionResolver implements HandlerExceptionResolver {
    @Value("${spring.application.name:Unknown}")
    private String applicationName;

    @Value("${feign.service.rpcErrorHttpStatus:520}")
    private Integer rpcErrorHttpStatus;

    @Value("${feign.service.useCodeAsStatus:true}")
    private Boolean useCodeAsStatus;

    @SneakyThrows
    @Override
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        String tag = request.getHeader(RpcConstants.RPC_TAG_HEADER);
        if (!RpcConstants.RPC_TAG_VALUE.equals(tag)) {
            return null;
        }

        response.addHeader(RpcConstants.RPC_REMOTE_APPLICATION_NAME_HEADER, this.applicationName);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        if (e instanceof CodeBasedException codeBasedException) {
            if (useCodeAsStatus) {
                response.setStatus(codeBasedException.getErrorCode());
            } else {
                response.setStatus(rpcErrorHttpStatus);
            }
            RpcErrorResult errorResult = new RpcErrorResult();
            errorResult.setCode(codeBasedException.getErrorCode());
            errorResult.setMsg(codeBasedException.getErrorMsg());
            log.warn("Rpc Exception, return is code {}, Result {}", response.getStatus(), errorResult, e);
            String json = RpcErrorResultSerializer.encode(errorResult);
            response.getWriter().print(json);
        } else {
            response.setStatus(rpcErrorHttpStatus);
            RpcErrorResult errorResult = new RpcErrorResult();
            errorResult.setCode(RpcConstants.PRC_RESULT_CODE_DEF);
            errorResult.setMsg(e.getMessage());
            log.warn("Rpc Exception, return is code {}, Result {}", response.getStatus(), errorResult, e);
            String json = RpcErrorResultSerializer.encode(errorResult);
            response.getWriter().print(json);
        }
        return new ModelAndView();
    }

}
