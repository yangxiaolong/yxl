package com.lego.yxl.core.singlequery.support;

import com.lego.yxl.core.singlequery.annotation.MaxResultCheckStrategy;
import com.lego.yxl.core.singlequery.ManyResultException;
import com.lego.yxl.core.singlequery.MaxResultConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public abstract class AbstractQueryRepository<E> {
    protected void processForMaxResult(Object query, MaxResultConfig maxResultConfig, List<E> entities) {
        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.LOG){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.warn("【LOG】result size is {} more than {}, dao is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.getDao(),
                        query);
                return;
            }
        }

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.ERROR){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.error("【ERROR】result size is {} more than {}, dao is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.getDao(),
                        query);
                throw new ManyResultException();
            }
        }

        if (maxResultConfig.getCheckStrategy() == MaxResultCheckStrategy.SET_LIMIT){
            if (entities.size() >= maxResultConfig.getMaxResult()){
                log.error("【SET_LIMIT】result size is {} more than {}, please find and fix, dao is {} param is {}", entities.size(),
                        maxResultConfig.getMaxResult(),
                        this.getDao(),
                        query);
                return;
            }
        }
    }

    protected abstract Object getDao();
}
