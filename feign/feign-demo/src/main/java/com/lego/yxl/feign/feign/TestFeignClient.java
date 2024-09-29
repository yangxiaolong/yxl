package com.lego.yxl.feign.feign;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "testFeignClient", url = "http://127.0.0.1:9090")
public interface TestFeignClient extends TestFeignApi{
}
