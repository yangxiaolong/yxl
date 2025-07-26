package com.lego.yxl.wide.support;

import com.google.common.collect.Lists;
import com.lego.yxl.wide.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class SimpleWidePatrolService<
        MASTER_ID, // 主数据 id
        WIDE extends Wide<MASTER_ID, ITEM_TYPE>, // 宽表类型
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>> // 关联数据类型
        extends BaseSimpleWideService<MASTER_ID, ITEM_TYPE, WIDE>
        implements WidePatrolService<MASTER_ID, ITEM_TYPE> {

    private Consumer<List<MASTER_ID>> reindexConsumer;

    private void reindexByMasterId(List<MASTER_ID> ids) {
        if (CollectionUtils.isNotEmpty(ids) && reindexConsumer != null) {
            reindexConsumer.accept(ids);
        }
    }

    @Override
    public void index(MASTER_ID masterId) {
        if (masterId == null) {
            return;
        }
        index(Collections.singletonList(masterId));
    }

    @Override
    public void index(List<MASTER_ID> masterIds) {
        List<WIDE> widesInDb = getWideCommandRepository().findByIds(masterIds);
        Map<MASTER_ID, WIDE> wideInDBMap = widesInDb.stream()
                .collect(Collectors.toMap(Wide::getId, Function.identity()));

        List<WIDE> widesInMem = createAndBindItemDatas(masterIds);

        List<MASTER_ID> errorIds = Lists.newArrayList();

        for (WIDE mem : widesInMem) {
            MASTER_ID id = mem.getId();
            WIDE db = wideInDBMap.get(id);

            if (db == null) {
                log.warn("id {} lost", id);
                errorIds.add(id);
            } else {
                if (!isEquals(db, mem)) {
                    log.warn("id {} data not equals, db: {} and {}", id, db, mem);
                    errorIds.add(id);
                } else {
                    log.info("id {} is same", id);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(errorIds)) {
            reindexByMasterId(errorIds);
        }
    }

    private boolean isEquals(WIDE db, WIDE mem) {
        return Objects.equals(db, mem);
    }

    @Override
    public <KEY> void updateItem(ITEM_TYPE itemType, KEY key) {
        WideItemDataProvider<ITEM_TYPE, Object, ? extends WideItemData<ITEM_TYPE, ?>> wideItemDataProvider = findWideItemDataProvider(itemType);
        if (wideItemDataProvider == null) {
            log.warn("Failed to find data provider for type {}", itemType);
            return;
        }
        WideItemData<ITEM_TYPE, ?> itemData = wideItemDataProvider.apply(key);

        List<MASTER_ID> errorIds = Lists.newArrayList();
        this.getWideCommandRepository().consumeByItem(itemType, key, wide -> {
            WideWrapper<WIDE> wrapperForWide = createWrapperForWide(wide);
            WideIndexCompareContext<ITEM_TYPE> context = new WideIndexCompareContext<>(itemType, itemData, wrapperForWide);
            if (!wide.isSameWithItem(context)) {
                log.info("id {} Item Not Same, item {}, wide {}", wide.getId(), itemData, wide);
                errorIds.add(wide.getId());
            }
        });

        reindexByMasterId(errorIds);
    }
}
