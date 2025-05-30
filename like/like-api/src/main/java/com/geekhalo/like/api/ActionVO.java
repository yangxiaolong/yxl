package com.geekhalo.like.api;

import lombok.Data;

@Data
public class ActionVO {
    private String targetType;
    private Long targetId;
    private Long userId;
    private boolean valid;
}
