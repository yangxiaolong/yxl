package com.geekhalo.like.feign.client;

import com.geekhalo.like.api.ActionCommandApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${like.server.name}", path = ActionCommandApi.PATH)
public interface ActionCommandFeignClient extends ActionCommandApi {
}
