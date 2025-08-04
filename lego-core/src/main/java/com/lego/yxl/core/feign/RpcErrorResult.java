package com.lego.yxl.core.feign;

import lombok.Data;


@Data
public class RpcErrorResult {
    private int code;
    private String msg;
}
