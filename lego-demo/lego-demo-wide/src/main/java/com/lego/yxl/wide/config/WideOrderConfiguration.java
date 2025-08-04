package com.lego.yxl.wide.config;

import com.lego.yxl.core.wide.*;
import com.lego.yxl.wide.core.WideOrderPatrolService;
import com.lego.yxl.wide.core.WideOrderRepository;
import com.lego.yxl.wide.core.WideOrderType;
import com.lego.yxl.wide.es.WideOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WideOrderConfiguration extends WideConfigurationSupport<Long, WideOrderType, WideOrder> {

    @Autowired
    private WideOrderRepository wideOrderRepository;

    @Autowired
    private List<WideItemDataProvider<WideOrderType, ?, ? extends WideItemData<WideOrderType, ?>>> wideItemDataProviders;

    @Bean
    public WideIndexService<Long, WideOrderType> createWideIndexService() {
        return super.createWideIndexService();
    }

    @Bean
    public WideOrderPatrolService wideOrderPatrolService() {
        return new WideOrderPatrolService(createWidePatrolService());
    }

    @Bean
    protected WideService<Long, WideOrderType> createWideService(
            WideIndexService<Long, WideOrderType> wideIndexService,
            WideOrderPatrolService wideOrderPatrolService) {
        return super.createWideService(wideIndexService, wideOrderPatrolService);
    }


    @Override
    protected WideFactory<Long, WideOrder> getWideFactory() {
        return WideOrder::new;
    }

    @Override
    protected WideCommandRepository<Long, WideOrderType, WideOrder> getWideCommandRepository() {
        return this.wideOrderRepository;
    }

    @Override
    protected List<WideItemDataProvider<WideOrderType, ?, ? extends WideItemData<WideOrderType, ?>>> getWideItemProviders() {
        return this.wideItemDataProviders;
    }
}
