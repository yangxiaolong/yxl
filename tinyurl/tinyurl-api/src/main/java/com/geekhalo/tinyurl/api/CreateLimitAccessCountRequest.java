package com.geekhalo.tinyurl.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLimitAccessCountRequest {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;

    @NotNull
    private Integer maxCount;
}
