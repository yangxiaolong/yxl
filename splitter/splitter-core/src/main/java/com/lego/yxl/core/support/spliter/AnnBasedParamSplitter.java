package com.lego.yxl.core.support.spliter;

import com.lego.yxl.core.ParamSplitter;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 参数拆分器公共父类
 */
public class AnnBasedParamSplitter extends AbstractParamSplitter{
    private final ParamSplitter paramSplitter;
    private final Field splitField;

    public AnnBasedParamSplitter(Class paramCls,
                                 ParamSplitter paramSplitter,
                                 Field splitField) {
        this.paramSplitter = paramSplitter;
        this.splitField = splitField;
    }


    @Override
    protected List<Object> doSplit(Object param, int maxSize) {
        try {
            Class<?> aClass = param.getClass();
            Object splitValue = FieldUtils.readField(this.splitField, param, true);
            List<Object> split = this.paramSplitter.split(splitValue, maxSize);
            return split.stream()
                    .map(value-> cloneBySplittedValue(aClass, param, value))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Object cloneBySplittedValue(Class<?> aClass, Object source, Object splittedFieldValue) {
        try {
            Object o = ConstructorUtils.invokeConstructor(aClass);
            FieldUtils.getAllFieldsList(aClass).forEach(field -> {
                try {
                    Object v = null;
                    if (field.equals(splitField)){
                        v = splittedFieldValue;
                    }else {
                        v = FieldUtils.readField(field, source, true);
                    }
                    FieldUtils.writeField(field, o, v, true);
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            });
            return o;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
