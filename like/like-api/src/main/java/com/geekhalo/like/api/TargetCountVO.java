package com.geekhalo.like.api;

import lombok.Data;

@Data
public class TargetCountVO {
    private String targetType;
    private Long targetId;
    private Long count;
}
