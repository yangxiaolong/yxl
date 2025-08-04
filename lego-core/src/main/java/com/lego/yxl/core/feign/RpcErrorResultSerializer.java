package com.lego.yxl.core.feign;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;


public class RpcErrorResultSerializer {
    public static RpcErrorResult decode(String json){
        if (StringUtils.isEmpty(json)){
            return null;
        }
        return JSON.parseObject(json, RpcErrorResult.class);
    }

    public static String encode(RpcErrorResult rpcErrorResult){
        if (rpcErrorResult == null){
            return null;
        }
        return JSON.toJSONString(rpcErrorResult);
    }
}
