package com.geekhalo.tinyurl.domain;

import com.lego.yxl.command.core.CommandForCreate;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
abstract class AbstractCreateTinyUrlCommand implements CommandForCreate {
    @NotEmpty
    private String url;

    private Boolean enableCache;

    private Boolean enableCacheSync;
}
