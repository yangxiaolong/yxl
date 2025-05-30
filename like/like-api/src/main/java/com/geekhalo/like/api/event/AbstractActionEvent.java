package com.geekhalo.like.api.event;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Data
@Setter(AccessLevel.PRIVATE)
abstract class AbstractActionEvent {
    private Long userId;

    private String targetType;

    private Long targetId;

    protected void init(Long userId, String targetType, Long targetId){
        Preconditions.checkArgument(userId != null);
        Preconditions.checkArgument(StringUtils.isNoneBlank(targetType));
        Preconditions.checkArgument(targetId != null);

        setUserId(userId);
        setTargetType(targetType);
        setTargetId(targetId);
    }
}
