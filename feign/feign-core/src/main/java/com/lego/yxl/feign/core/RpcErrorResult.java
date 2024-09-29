package com.lego.yxl.feign.core;

import lombok.Data;


@Data
public class RpcErrorResult {
    private int code;
    private String msg;
}
