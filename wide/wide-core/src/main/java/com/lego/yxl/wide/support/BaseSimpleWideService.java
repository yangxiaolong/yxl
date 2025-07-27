package com.lego.yxl.wide.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lego.yxl.wide.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseSimpleWideService<
        MASTER_ID, // 主数据 id
        TYPE extends Enum<TYPE> & WideItemType<TYPE>,
        WIDE extends Wide<MASTER_ID, TYPE>> {
    @Setter
    @Getter(AccessLevel.PRIVATE)
    private WideWrapperFactory<WIDE> wideWrapperFactory;

    @Getter(AccessLevel.PROTECTED)
    private final List<WideItemDataProvider<TYPE, ? extends Object, ? extends WideItemData<TYPE, ?>>>
            wideItemDataProviders = Lists.newArrayList();

    @Getter(AccessLevel.PROTECTED)
    @Setter
    private WideCommandRepository<MASTER_ID, TYPE, WIDE> wideCommandRepository;

    @Getter(AccessLevel.PROTECTED)
    @Setter
    private WideFactory<MASTER_ID, WIDE> wideFactory;

    protected List<WIDE> createAndBindItemDatas(List<MASTER_ID> ids) {
        List<WIDE> wides = createWides(ids);
        bindItemDatas(wides);
        return wides;
    }

    private List<WIDE> createWides(List<MASTER_ID> ids) {
        return ids.stream()
                .filter(Objects::nonNull)
                .map(data -> this.wideFactory.create(data))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void bindItemDatas(List<WIDE> wides) {
        Map<WIDE, WideWrapper<WIDE>> wrapperCache = Maps.newHashMap();

        for (WideItemDataProvider<TYPE, ?, ? extends WideItemData<TYPE, ?>> itemProvider
                : this.wideItemDataProviders) {
            TYPE supportType = itemProvider.getSupportType();
            // 获取关联 Key 信息
            Set<Object> allItemKeys = getAllItemsKeys(wides, supportType);

            // 加载数据
            List itemData = itemProvider.apply((List) Lists.newArrayList(allItemKeys));

            // 以 Key 为 建，对数据进行转换
            Map itemMap = (Map) itemData.stream()
                    .collect(Collectors.toMap(v -> ((WideItemData) v).getKey(), v -> v));

            for (WIDE wide : wides) {
                // 获取单个 wide 的 Item key 信息
                List itemsKeys = wide.getItemsKeyByType(supportType).stream()
                        .filter(Objects::nonNull)
                        .map(WideItemKey::getKey)
                        .collect(Collectors.toList());
                // 转为为 item Data 信息
                List itemDatas = (List) itemsKeys.stream()
                        .map(itemMap::get)
                        .collect(Collectors.toList());

                WideWrapper<WIDE> wideWrapper = wrapperCache.computeIfAbsent(wide,
                        w -> this.wideWrapperFactory.createForWide(w));

                // 执行内存更新
                wide.updateByItem(new WideIndexBatchUpdateContext<>(supportType, itemDatas, wideWrapper));
            }
        }
    }

    private Set<Object> getAllItemsKeys(List<WIDE> wides, TYPE supportType) {
        return wides.stream()
                .flatMap(wide -> wide.getItemsKeyByType(supportType).stream())
                .filter(Objects::nonNull)
                .map(WideItemKey::getKey)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    protected WideItemDataProvider<TYPE, Object, ? extends WideItemData<TYPE, ?>>
    findWideItemDataProvider(TYPE itemType) {
        return (WideItemDataProvider<TYPE, Object, ? extends WideItemData<TYPE, ?>>)
                this.getWideItemDataProviders().stream()
                        .filter(wideItemDataProvider -> wideItemDataProvider.support(itemType))
                        .findFirst()
                        .orElse(null);
    }

    protected WideWrapper<WIDE> createWrapperForWide(WIDE wide) {
        return getWideWrapperFactory().createForWide(wide);
    }

    public void setWideItemDataProviders(List<WideItemDataProvider
            <TYPE, ?, ? extends WideItemData<TYPE, ?>>> wideItemProviders) {
        if (wideItemProviders != null) {
            this.wideItemDataProviders.addAll(wideItemProviders);
        }

        AnnotationAwareOrderComparator.sort(this.wideItemDataProviders);
    }
}
