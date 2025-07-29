package com.lego.yxl.wide;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

@Getter(AccessLevel.PRIVATE)
public class WideService<
        MASTER_DATA_ID, // 主数据 ID
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE> // 关联数据类型
        > {
    private final WideIndexService<MASTER_DATA_ID, ITEM_TYPE> indexService;
    private final WidePatrolService<MASTER_DATA_ID, ITEM_TYPE> patrolService;

    public WideService(WideIndexService<MASTER_DATA_ID, ITEM_TYPE> indexService,
                       WidePatrolService<MASTER_DATA_ID, ITEM_TYPE> patrolService) {
        this.indexService = indexService;
        this.patrolService = patrolService;

        init();
    }

    public void init() {
        Preconditions.checkArgument(indexService != null);
        if (this.patrolService != null) {
            this.patrolService.setReindexConsumer(this::index);
        }
    }

    public void index(MASTER_DATA_ID id) {
        if (this.patrolService != null) {
            this.patrolService.index(id);
        }
        this.indexService.index(id);
    }

    public void index(List<MASTER_DATA_ID> ids) {
        if (this.patrolService != null) {
            this.patrolService.index(ids);
        }
        this.indexService.index(ids);
    }

    public <KEY> void updateItem(ITEM_TYPE type, KEY key) {
        if (this.patrolService != null) {
            this.patrolService.updateItem(type, key);
        }
        this.indexService.updateItem(type, key);
    }
}
