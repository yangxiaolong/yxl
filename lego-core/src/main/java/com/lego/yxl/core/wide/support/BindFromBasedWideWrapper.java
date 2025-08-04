package com.lego.yxl.core.wide.support;

import com.lego.yxl.core.wide.Wide;
import com.lego.yxl.core.wide.WideWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class BindFromBasedWideWrapper<W extends Wide> implements WideWrapper<W> {
    private final W wideObject;
    private final Map<Class, List<FieldMapper>> fieldMappers;
    private final ConversionService conversionService;

    public BindFromBasedWideWrapper(W wideObject,
                                    Map<Class, List<FieldMapper>> fieldMappers,
                                    ConversionService conversionService) {
        this.wideObject = wideObject;
        this.fieldMappers = fieldMappers;
        this.conversionService = conversionService;
    }

    @Override
    public W getTarget() {
        return this.wideObject;
    }

    @Override
    public <I> boolean isSameWithItem(I item) {
        if (item == null) {
            return false;
        }
        List<FieldMapper> fieldMappers = this.fieldMappers.get(item.getClass());
        if (!CollectionUtils.isEmpty(fieldMappers)) {
            for (FieldMapper fieldMapper : fieldMappers) {
                try {
                    Object valueOnWide = FieldUtils.readField(fieldMapper.getWideField(), this.wideObject, true);
                    Object valueOnItem = FieldUtils.readField(fieldMapper.getItemField(), item, true);
                    Object valueOnItemAfterConvert = this.conversionService
                            .convert(valueOnItem, fieldMapper.getWideField().getType());
                    if (!Objects.equals(valueOnItemAfterConvert, valueOnWide)) {
                        return false;
                    }
                } catch (Exception e) {
                    log.error("failed to check:", e);
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public <I> void updateItem(I item) {
        if (item == null) {
            return;
        }
        List<FieldMapper> fieldMappers = this.fieldMappers.get(item.getClass());
        if (!CollectionUtils.isEmpty(fieldMappers)) {
            for (FieldMapper fieldMapper : fieldMappers) {
                try {
                    Object valueOnItem = FieldUtils.readField(fieldMapper.getItemField(), item, true);
                    Object valueOnItemAfterConvert = this.conversionService
                            .convert(valueOnItem, fieldMapper.getWideField().getType());
                    FieldUtils.writeField(fieldMapper.getWideField(),
                            this.wideObject, valueOnItemAfterConvert, true);
                } catch (Exception e) {
                    log.error("failed to check:", e);
                }
            }
        }
    }
}
