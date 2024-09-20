package com.lego.yxl.support.handler.aggsyncer;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Arrays;
import java.util.List;

public class SmartAggSyncers{
    private List<SmartAggSyncer> aggSyncers = Lists.newArrayList();

    public SmartAggSyncer findAggSyncer(Class aggClass){
        return this.aggSyncers.stream()
                .filter(smartAggSyncer -> smartAggSyncer.apply(aggClass))
                .findFirst()
                .orElseThrow(() -> new AggSyncerNotFoundException(aggClass));
    }

    public SmartAggSyncer findAggSyncerOrNull(Class aggClass){
        return this.aggSyncers.stream()
                .filter(smartAggSyncer -> smartAggSyncer.apply(aggClass))
                .findFirst()
                .orElse(null);
    }

    @Autowired(required = false)
    public void setAggSyncers(List<SmartAggSyncer> syncers){
        addAggSyncers(syncers);
    }

    public void addAggSyncer(SmartAggSyncer smartAggSyncer){
        addAggSyncers(Arrays.asList(smartAggSyncer));
    }

    private void addAggSyncers(List<SmartAggSyncer> syncers){
        this.aggSyncers.addAll(syncers);
        AnnotationAwareOrderComparator.sort(this.aggSyncers);
    }
}
