package com.lego.yxl.core.jpa.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lego.yxl.annotation.EmbeddedFilter;
import com.lego.yxl.core.ValueContainer;
import com.lego.yxl.core.jpa.SpecificationConverter;
import com.lego.yxl.core.jpa.support.handler.JpaAnnotationHandler;
import com.lego.yxl.core.support.AbstractQueryConverter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DefaultSpecificationConverter<E>
        extends AbstractQueryConverter<Specification<E>>
        implements SpecificationConverter<E> {

    private final Class<E> entityCls;
    private final List<JpaAnnotationHandler> annotationHandlers;

    public DefaultSpecificationConverter(Class<E> entityCls,
                                         List<JpaAnnotationHandler> annotationHandlers) {
        Preconditions.checkArgument(entityCls != null);
        Preconditions.checkArgument(!CollectionUtils.isEmpty(annotationHandlers));
        this.entityCls = entityCls;
        this.annotationHandlers = annotationHandlers;
    }

    @Override
    public Specification<E> convertForQuery(Object query) {
        if (query == null){
            return null;
        }

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> ps = createPredicates(root, criteriaQuery, criteriaBuilder, query);
            return criteriaQuery.where(ps.toArray(new Predicate[ps.size()])).getRestriction();
        };
    }

    private List<Predicate> createPredicates(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, Object queryObject){
        if (queryObject == null){
            return Collections.emptyList();
        }

        Class cls = queryObject.getClass();
        List<Predicate> ps = Lists.newArrayList();
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
        for (Field field : allFieldsList){
            try {
                Object value = FieldUtils.readField(field, queryObject, true);
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
                    for (JpaAnnotationHandler handler : annotationHandlers){
                        if (handler.support(annotation)){
                            Predicate predicate = handler.create(root, criteriaQuery, criteriaBuilder, annotation, value);
                            if (predicate != null){
                                ps.add(predicate);
                            }
                        }
                    }

                    if (annotation.annotationType() == EmbeddedFilter.class){
                        ps.addAll(createPredicates(root, criteriaQuery, criteriaBuilder, value));
                    }
                }
            }catch (Exception e){
                log.error("field to bind filter", e);
            }
        }
        return ps;
    }

    @Override
    public Specification<E> convertForCount(Object query) {
        if (query == null){
            return null;
        }

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> ps = createPredicates(root, criteriaQuery, criteriaBuilder, query);
            return criteriaQuery.where(ps.toArray(new Predicate[ps.size()])).getRestriction();
        };
    }

    @Override
    public void validate(Class cls) {
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
        for (Field field : allFieldsList){
            Class fieldType = field.getType();
            Class valueType = fieldType;
            if (ValueContainer.class.isAssignableFrom(fieldType)){
                valueType = List.class;
            }

            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations){
                for (JpaAnnotationHandler handler : this.annotationHandlers){
                    if (handler.support(annotation)){
                        Field entityField = handler.findEntityField(this.entityCls, annotation, valueType);
                        if (entityField == null){
                            throw new RuntimeException("Can not find field for " + field.getName() + " on class " + cls);
                        }
                    }
                }

                if (annotation.annotationType() == EmbeddedFilter.class){
                    validate(fieldType);
                }
            }
        }
    }
}
