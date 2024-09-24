package com.lego.yxl.core.support;

import com.lego.yxl.core.Pageable;
import com.lego.yxl.core.QueryConverter;
import com.lego.yxl.core.Sort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;



@Slf4j
public abstract class AbstractQueryConverter<E> implements QueryConverter<E> {
    public Sort findSort(Object o) {
        Sort sort = null;
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(o.getClass());
        for (Field field : allFieldsList){
            if (field.getType() == Sort.class){
                try {
                    sort = (Sort) FieldUtils.readField(field, o, true);
                }catch (Exception e){
                    log.error("failed to bind sort from {}", o);
                }
                break;
            }
        }
        return sort;
    }

    @Override
    public Pageable findPageable(Object query) {
        Pageable pageable = null;
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(query.getClass());
        for (Field field : allFieldsList){
            if (field.getType() == Pageable.class){
                try {
                    pageable = (Pageable) FieldUtils.readField(field, query, true);
                }catch (Exception e){
                    log.error("failed to find pageable  from {}", query);
                }
                break;
            }
        }
        return pageable;
    }
}
