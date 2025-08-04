package com.lego.yxl.core.singlequery.mybatis.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.core.singlequery.annotation.MaxResultCheckStrategy;
import com.lego.yxl.core.singlequery.MaxResultConfig;
import com.lego.yxl.core.singlequery.MaxResultConfigResolver;
import com.lego.yxl.core.singlequery.Page;
import com.lego.yxl.core.singlequery.Pageable;
import com.lego.yxl.core.singlequery.mybatis.ExampleConverter;
import com.lego.yxl.core.singlequery.mybatis.ExampleConverterFactory;
import com.lego.yxl.core.singlequery.mybatis.ExampleSingleQueryRepository;
import com.lego.yxl.core.singlequery.support.AbstractQueryRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class BaseReflectBasedExampleSingleQueryRepository<E, ID>
        extends AbstractQueryRepository<E>
        implements ExampleSingleQueryRepository<E, ID> {
    private final Object mapper;
    private final Class exampleCls;
    @Getter(AccessLevel.PROTECTED)
    private ExampleConverter exampleConverter;
    private MaxResultConfigResolver maxResultConfigResolver;

    @Autowired
    private ExampleConverterFactory exampleConverterFactory;

    public BaseReflectBasedExampleSingleQueryRepository(Object mapper, Class exampleClass){
        Preconditions.checkArgument(mapper != null);
        Preconditions.checkArgument(exampleClass != null);
        this.mapper = mapper;
        this.exampleCls = exampleClass;
    }

    @Override
    protected Object getDao() {
        return this.mapper;
    }

    @PostConstruct
    public void init(){
        if (exampleConverter == null){
            this.exampleConverter = exampleConverterFactory.createFor(this.exampleCls);
        }
        if (maxResultConfigResolver == null){
            this.maxResultConfigResolver = new AnnoBasedMaxResultConfigResolver();
        }

        Preconditions.checkArgument(this.exampleConverter != null);
        Preconditions.checkArgument(this.maxResultConfigResolver != null);
    }

    @Override
    public E getById(ID id) {
        if (id == null){
            return null;
        }
        return doSelectByPrimaryKey(id);
    }

    @Override
    public List<E> getByIds(List<ID> ids) {
        throw new NotImplementedException("Need Override");
    }

    @Override
    public void checkForQueryObject(Class cls) {
        this.exampleConverter.validate(cls);
    }

    @Override
    public <Q> List<E> listOf(Q query) {
        if (query == null){
            return Collections.emptyList();
        }

        Object example = this.exampleConverter.convertForQuery(query);

        MaxResultConfig maxResultConfig = this.maxResultConfigResolver.maxResult(query);

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            setLimitForExample(maxResultConfig.getMaxResult(), example);
        }

        List<E> entities = doList(example);

        if (CollectionUtils.isEmpty(entities)){
            return Collections.emptyList();
        }

        processForMaxResult(query, maxResultConfig, entities);

        return entities;
    }

    private void setLimitForExample(Integer maxResult, Object example) {
        try {
            MethodUtils.invokeMethod(example, true, "setRows", maxResult);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "setRows", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public <Q> E get(Q query) {
        if (query == null){
            return null;
        }

        Object example = this.exampleConverter.convertForQuery(query);

        List<E> entities = doList(example);

        if (CollectionUtils.isEmpty(entities)){
            return null;
        }
        return entities.stream()
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }

    private  <Q> Pageable findPageable(Q query){
        return exampleConverter.findPageable(query);
    }

    @Override
    public <Q> Page<E> pageOf(Q query) {
        Pageable pageable = findPageable(query);

        if (pageable == null){
            throw new IllegalArgumentException("Pageable Lost");
        }

        Long totalElement = countOf(query);

        List<E> content =  listOf(query);

        return new Page<>(content, pageable, totalElement);
    }

    private E doSelectByPrimaryKey(ID id){
        try {
            return (E) MethodUtils.invokeMethod(this.mapper, true, "selectByPrimaryKey", id);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "selectByExample", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }

    private List<E> doList(Object example){
        try {
            return (List<E>) MethodUtils.invokeMethod(this.mapper, true, "selectByExample", example);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "selectByExample", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <R> Long countOf(R request) {
        if (request == null){
            return null;
        }

        Object example = this.exampleConverter.convertForCount(request);

        return doCount(example);
    }

    private Long doCount(Object example) {
        try {
            return (Long) MethodUtils.invokeMethod(this.mapper, true, "countByExample", example);
        } catch (Exception e) {
            log.error("failed to invoke method {} of {} ", "countByExample", this.mapper);
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }

}
