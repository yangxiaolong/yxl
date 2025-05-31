package com.geekhalo.tinyurl.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CreateExpireRequest {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;

    private Date beginTime;

    @NotNull
    private Date expireTime;
}
