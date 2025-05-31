package com.geekhalo.tinyurl.api;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCommonRequest {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;
}
