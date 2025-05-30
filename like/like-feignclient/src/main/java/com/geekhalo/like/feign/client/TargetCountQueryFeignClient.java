package com.geekhalo.like.feign.client;

import com.geekhalo.like.api.ActionCommandApi;
import com.geekhalo.like.api.TargetCountQueryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${like.server.name}", path = ActionCommandApi.PATH)
public interface TargetCountQueryFeignClient extends TargetCountQueryApi {
}
