package com.lego.yxl.loader.core.propertylazyloader;

import com.lego.yxl.loader.core.annotation.LazyLoadBy;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertyLazyLoaderFactory {

    private final ApplicationContext applicationContext;

    public PropertyLazyLoaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<PropertyLazyLoader> createFor(Class<?> cls) {
        return FieldUtils.getAllFieldsList(cls).stream()
                .map(this::createFromField)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PropertyLazyLoader createFromField(Field field) {
        LazyLoadBy mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, LazyLoadBy.class);
        if (mergedAnnotation == null) {
            return null;
        }

        String targetEl = mergedAnnotation.value();
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            AnnotationAttributes mergedAnnotationAttributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                    field, annotation.annotationType(), true, true);
            if (CollectionUtils.isEmpty(mergedAnnotationAttributes)) {
                continue;
            }
            Set<Map.Entry<String, Object>> entries = mergedAnnotationAttributes.entrySet();
            if (CollectionUtils.isEmpty(entries)) {
                continue;
            }

            for (Map.Entry<String, Object> entry : entries) {
                String key = "${" + entry.getKey() + "}";
                String value = String.valueOf(entry.getValue());
                targetEl = targetEl.replace(key, value);
            }
        }

        return new PropertyLazyLoader(field, new BeanFactoryResolver(this.applicationContext), targetEl);
    }
}
