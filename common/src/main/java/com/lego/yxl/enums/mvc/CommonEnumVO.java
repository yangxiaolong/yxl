package com.lego.yxl.enums.mvc;

import com.lego.yxl.enums.CommonEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonEnumVO {
    private final int code;

    private final String name;

    private final String desc;

    public static CommonEnumVO from(CommonEnum commonEnum) {
        if (commonEnum == null) {
            return null;
        }
        return new CommonEnumVO(commonEnum.getCode(), commonEnum.getName(), commonEnum.getDescription());
    }

    public static List<CommonEnumVO> from(List<CommonEnum> commonEnums) {
        if (CollectionUtils.isEmpty(commonEnums)) {
            return Collections.emptyList();
        }
        return commonEnums.stream()
                .filter(Objects::nonNull)
                .map(CommonEnumVO::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
