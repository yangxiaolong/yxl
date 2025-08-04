package com.lego.yxl.core.wide.support;

import com.google.common.collect.Maps;
import com.lego.yxl.core.wide.BindFrom;
import com.lego.yxl.core.wide.Wide;
import com.lego.yxl.core.wide.WideWrapper;
import com.lego.yxl.core.wide.WideWrapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BindFromBasedWideWrapperFactory<W extends Wide> implements WideWrapperFactory<W> {
    private final ConversionService conversionService;
    private final Map<Class, Map<Class, List<FieldMapper>>> fieldMappersCache = Maps.newHashMap();

    public BindFromBasedWideWrapperFactory(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public WideWrapper<W> createForWide(W wide) {
        if (wide == null) {
            return null;
        }
        Map<Class, List<FieldMapper>> fieldMappers =
                this.fieldMappersCache.computeIfAbsent(wide.getClass(), this::parse);
        return new BindFromBasedWideWrapper<>(wide, fieldMappers, this.conversionService);
    }

    private Map<Class, List<FieldMapper>> parse(Class<?> wideClass) {
        Map<Class, List<FieldMapper>> result = new HashMap<>();
        Field[] fields = FieldUtils.getFieldsWithAnnotation(wideClass, BindFrom.class);
        for (Field wideField : fields) {
            BindFrom bindFrom = AnnotatedElementUtils.findMergedAnnotation(wideField, BindFrom.class);
            Field itemField = FieldUtils.getField(bindFrom.sourceClass(), bindFrom.field(), true);
            if (itemField != null) {
                FieldMapper fieldMapper = new FieldMapper(itemField, wideField);
                result.computeIfAbsent(bindFrom.sourceClass(), cls -> new ArrayList<>())
                        .add(fieldMapper);
            } else {
                log.warn("Field Config Error, Field Not Found on {} {}", wideField, bindFrom);
            }
        }
        return result;
    }
}
