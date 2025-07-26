package com.lego.yxl.wide.support;

import com.lego.yxl.wide.*;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Setter(AccessLevel.PUBLIC)
public class SimpleWideIndexService<
        MASTER_ID, // 主数据 id
        WIDE extends Wide<MASTER_ID, ITEM_TYPE>, // 宽表类型
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>> // 关联数据类型
        extends BaseSimpleWideService<MASTER_ID, ITEM_TYPE, WIDE>
        implements WideIndexService<MASTER_ID, ITEM_TYPE> {

    @Override
    public void index(MASTER_ID id) {
        if (id == null) {
            return;
        }
        index(Collections.singletonList(id));
    }

    @Override
    public void index(List<MASTER_ID> ids) {
        List<WIDE> wides = createAndBindItemDatas(ids);
        batchSave(wides);
    }

    @Override
    public <KEY> void updateItem(ITEM_TYPE itemType, KEY key) {
        WideItemDataProvider<ITEM_TYPE, Object, ? extends WideItemData<ITEM_TYPE, ?>> wideItemProvider
                = findWideItemDataProvider(itemType);
        if (wideItemProvider == null) {
            log.warn("failed to find data provider for {}", itemType);
            return;
        }

        WideItemData<ITEM_TYPE, ?> wideItem = wideItemProvider.apply(key);
        if (wideItem == null) {
            log.warn("failed to create item for {} use {}", key, wideItemProvider);
            return;
        }
        updateItemData(wideItem);
    }

    private void batchSave(List<WIDE> wides) {
        List<WIDE> collect = wides.stream()
                .filter(Wide::isValidate)
                .collect(Collectors.toList());
        this.getWideCommandRepository().save(collect);
        log.info("success to save {}", collect);
    }


    public void updateItemData(WideItemData<ITEM_TYPE, ?> item) {
        ITEM_TYPE itemType = item.getItemType();
        Object key = item.getKey();
        // 支持批量处理，走批量处理
        if (this.getWideCommandRepository().supportUpdateFor(itemType)) {
            this.getWideCommandRepository().updateByItem(itemType, key, item);
        } else {
            // 不支持批量处理，内存更新并写回
            this.getWideCommandRepository().updateByItem(itemType, key, wide -> {
                WideWrapper<WIDE> forWide = createWrapperForWide(wide);
                WideIndexSingleUpdateContext<ITEM_TYPE> context = new WideIndexSingleUpdateContext<>
                        (item.getItemType(), item, forWide);
                wide.updateByItem(context);
            });
        }
    }
}
