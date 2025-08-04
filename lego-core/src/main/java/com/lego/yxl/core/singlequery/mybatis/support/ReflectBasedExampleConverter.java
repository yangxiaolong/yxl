package com.lego.yxl.core.singlequery.mybatis.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.core.singlequery.annotation.EmbeddedFilter;
import com.lego.yxl.core.singlequery.Pageable;
import com.lego.yxl.core.singlequery.Sort;
import com.lego.yxl.core.singlequery.ValueContainer;
import com.lego.yxl.core.singlequery.mybatis.ExampleConverter;
import com.lego.yxl.core.singlequery.mybatis.support.handler.FieldAnnotationHandler;
import com.lego.yxl.core.singlequery.support.AbstractQueryConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class ReflectBasedExampleConverter<E>
        extends AbstractQueryConverter<E>
        implements ExampleConverter<E> {

    private final Class<E> eClass;
    private final List<FieldAnnotationHandler> fieldAnnotationHandlers;

    public ReflectBasedExampleConverter(Class<E> eClass,
                                        List<FieldAnnotationHandler> fieldAnnotationHandlers) {
        Preconditions.checkArgument(eClass != null);
        Preconditions.checkArgument(fieldAnnotationHandlers != null);
        this.eClass = eClass;
        this.fieldAnnotationHandlers = fieldAnnotationHandlers;
    }

    @Override
    public E convertForQuery(Object query) {
        try {
            E example = instanceExample();

            Object criteria = instanceCriteria(example);

            bindWhere(query, criteria);

            bindSort(query, example);

            bindPageable(query, example);

            return example;
        } catch (Exception e) {
            log.error("failed to run convertForQuery", e);
        }
        return null;
    }

    @Override
    public E convertForCount(Object query) {
        try {
            E example = instanceExample();
            Object criteria = instanceCriteria(example);

            bindWhere(query, criteria);

            return example;
        } catch (Exception e) {
            log.error("failed to run convertForCount", e);
        }
        return null;
    }

    private Object instanceCriteria(E example) throws Exception {
        return MethodUtils.invokeExactMethod(example, "createCriteria");
    }

    private E instanceExample() throws Exception {
        return ConstructorUtils.invokeConstructor(this.eClass);
    }


    private void bindSort(Object o, E example) throws Exception {
        Sort sort = findSort(o);

        bindSort(example, sort);
    }

    private void bindSort(E example, Sort sort) throws Exception {
        if (sort != null){
            MethodUtils.invokeMethod(example, "setOrderByClause", sort.toOrderByClause());
        }
    }

    private void bindPageable(Object o, E example) throws Exception{
        Pageable pageable = findPageable(o);
        bindPageable(example, pageable);
    }

    private void bindPageable(E example, Pageable pageable) throws Exception{
        if (pageable != null){
            MethodUtils.invokeMethod(example, "setRows", pageable.limit());
            MethodUtils.invokeMethod(example, "setOffset", pageable.offset());
        }
    }

    private void bindWhere(Object o, Object criteria){
        Class cls = o.getClass();
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
        for (Field field : allFieldsList){
            try {
                Object value = FieldUtils.readField(field, o, true);
                if (value == null){
                    log.info("field {} on class {} is null", field.getName(), cls.getName());
                    continue;
                }

                if (value instanceof ValueContainer){
                    List values = ((ValueContainer)value).getValues();
                    if (CollectionUtils.isEmpty(values)){
                        continue;
                    }else {
                        value = values;
                    }
                }

                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations){
                    for (FieldAnnotationHandler handler : fieldAnnotationHandlers){
                        if (handler.support(annotation)){
                            handler.addCriteria(criteria, annotation, value);
                        }
                    }

                    if (annotation.annotationType() == EmbeddedFilter.class){
                        bindWhere(value, criteria);
                    }
                }
            }catch (Exception e){
                log.error("field to bind filter", e);
            }
        }
    }

    @Override
    public void validate(Class cls) {
        try {
            E example = instanceExample();

            Object criteria = instanceCriteria(example);

            testFilter(cls, criteria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void testFilter(Class cls, Object criteria) throws Exception{
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
        for (Field field : allFieldsList){
            Class fieldType = field.getType();
            Class valueType = fieldType;
            if (ValueContainer.class.isAssignableFrom(fieldType)){
                valueType = List.class;
            }

            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations){
                for (FieldAnnotationHandler handler : this.fieldAnnotationHandlers){
                    if (handler.support(annotation)){
                        Method criteriaMethod = handler.getCriteriaMethod(criteria.getClass(), annotation, valueType);
                        if (criteriaMethod == null){
                            throw new RuntimeException("Can not find method for " + field.getName() + " on class " + criteria.getClass());
                        }
                    }
                }

                if (annotation.annotationType() == EmbeddedFilter.class){
                    testFilter(fieldType, criteria);
                }
            }
        }
    }
}
