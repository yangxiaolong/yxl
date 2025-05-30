package com.geekhalo.like.domain.target;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(LoadActionTargetByTarget.BEAN_NAME)
public class DefaultActionTargetLoader {
    private List<SingleActionTargetLoader> singleActionTargetLoaders = Lists.newCopyOnWriteArrayList();

    public ActionTarget loadByTarget(String type, Long id){
        if (CollectionUtils.isNotEmpty(singleActionTargetLoaders)){
            return this.singleActionTargetLoaders.stream()
                    .filter(loader -> loader.support(type))
                    .map(loader -> loader.load(type, id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Autowired(required = false)
    public void setSingleActionTargetLoaders(List<SingleActionTargetLoader> loaders){
        this.singleActionTargetLoaders.addAll(loaders);
        AnnotationAwareOrderComparator.sort(this.singleActionTargetLoaders);
    }
}
