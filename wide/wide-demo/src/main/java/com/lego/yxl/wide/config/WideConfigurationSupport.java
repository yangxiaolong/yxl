package com.lego.yxl.wide.config;

import com.lego.yxl.wide.*;
import com.lego.yxl.wide.support.BindFromBasedWideWrapperFactory;
import com.lego.yxl.wide.support.SimpleWideIndexService;
import com.lego.yxl.wide.support.SimpleWidePatrolService;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.List;

public abstract class WideConfigurationSupport<
        MASTER_DATA_ID, // 主数据 ID
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>,
        WIDE extends Wide<MASTER_DATA_ID, ITEM_TYPE>
        > {

    protected WideService<MASTER_DATA_ID, ITEM_TYPE> createWideService(
            WideIndexService<MASTER_DATA_ID, ITEM_TYPE> wideIndexService,
            WidePatrolService<MASTER_DATA_ID, ITEM_TYPE> widePatrolService) {
        WideService<MASTER_DATA_ID, ITEM_TYPE> wideService = new WideService<>();
        wideService.setIndexService(wideIndexService);
        wideService.setPatrolService(widePatrolService);

        wideService.init();

        return wideService;
    }

    protected WideIndexService<MASTER_DATA_ID, ITEM_TYPE> createWideIndexService() {
        WideFactory<MASTER_DATA_ID, WIDE> wideFactory = getWideFactory();
        WideWrapperFactory<WIDE> wideWrapperFactory = getWideWrapperFactory();
        WideCommandRepository<MASTER_DATA_ID, ITEM_TYPE, WIDE> wideCommandRepository = getWideCommandRepository();
        List<WideItemDataProvider<ITEM_TYPE, ?, ? extends WideItemData<ITEM_TYPE, ?>>> wideItemProviders
                = getWideItemProviders();

        SimpleWideIndexService<MASTER_DATA_ID, WIDE, ITEM_TYPE> simpleWideIndexService = new SimpleWideIndexService<>();
        simpleWideIndexService.setWideWrapperFactory(wideWrapperFactory);
        simpleWideIndexService.setWideFactory(wideFactory);
        simpleWideIndexService.setWideItemDataProviders(wideItemProviders);
        simpleWideIndexService.setWideCommandRepository(wideCommandRepository);
        return simpleWideIndexService;
    }

    protected WidePatrolService<MASTER_DATA_ID, ITEM_TYPE> createWidePatrolService() {
        WideFactory<MASTER_DATA_ID, WIDE> wideFactory = getWideFactory();
        WideWrapperFactory<WIDE> wideWrapperFactory = getWideWrapperFactory();
        WideCommandRepository<MASTER_DATA_ID, ITEM_TYPE, WIDE> wideCommandRepository = getWideCommandRepository();
        List<WideItemDataProvider<ITEM_TYPE, ?, ? extends WideItemData<ITEM_TYPE, ?>>> wideItemProviders
                = getWideItemProviders();

        SimpleWidePatrolService<MASTER_DATA_ID, WIDE, ITEM_TYPE> widePatrolService = new SimpleWidePatrolService<>();
        widePatrolService.setWideWrapperFactory(wideWrapperFactory);
        widePatrolService.setWideFactory(wideFactory);
        widePatrolService.setWideItemDataProviders(wideItemProviders);
        widePatrolService.setWideCommandRepository(wideCommandRepository);
        return widePatrolService;
    }

    protected abstract WideFactory<MASTER_DATA_ID, WIDE> getWideFactory();

    protected abstract WideCommandRepository<MASTER_DATA_ID, ITEM_TYPE, WIDE> getWideCommandRepository();

    protected abstract List<WideItemDataProvider<ITEM_TYPE, ?, ? extends WideItemData<ITEM_TYPE, ?>>> getWideItemProviders();

    protected WideWrapperFactory<WIDE> getWideWrapperFactory() {
        return new BindFromBasedWideWrapperFactory<>(new GenericConversionService());
    }

}