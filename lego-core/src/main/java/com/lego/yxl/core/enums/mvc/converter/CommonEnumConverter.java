package com.lego.yxl.core.enums.mvc.converter;

import com.lego.yxl.core.enums.mvc.CommonEnumRegistry;
import com.lego.yxl.core.enums.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@Component
public class CommonEnumConverter implements ConditionalGenericConverter {
    @Autowired
    private CommonEnumRegistry enumRegistry;

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<?> type = targetType.getType();
        return enumRegistry.getClassDict().containsKey(type);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return enumRegistry.getClassDict().keySet().stream()
                .map(cls -> new ConvertiblePair(String.class, cls))
                .collect(Collectors.toSet());
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String value = (String) source;
        List<CommonEnum> commonEnums = this.enumRegistry.getClassDict().get(targetType.getType());
        return commonEnums.stream()
                .filter(commonEnum -> commonEnum.match(value))
                .findFirst()
                .orElse(null);
    }
}
