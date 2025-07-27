package com.lego.yxl.wide;

import com.google.common.collect.Lists;
import com.lego.yxl.wide.es.WideOrder;
import com.lego.yxl.wide.es.WideOrderESDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

@Repository
public class WideOrderRepository implements WideCommandRepository<Long, WideOrderType, WideOrder> {
    @Autowired
    private WideOrderESDao wideOrderDao;

    @Override
    public void save(List<WideOrder> wides) {
        wideOrderDao.saveAll(wides);
    }

    @Override
    public List<WideOrder> findByIds(List<Long> masterIds) {
        return Lists.newArrayList(wideOrderDao.findAllById(masterIds));
    }

    @Override
    public <KEY> void consumeByItem(WideOrderType wideOrderType, KEY key, Consumer<WideOrder> wideConsumer) {
        doUpdateByItem(wideOrderType, (Long) key, wideConsumer);
    }

    private void doUpdateByItem(WideOrderType wideOrderType, Long key, Consumer<WideOrder> wideConsumer) {
        switch (wideOrderType) {
            case PRODUCT:
                this.wideOrderDao.findByProductId(key).forEach(wideConsumer);
            case ADDRESS:
                this.wideOrderDao.findByAddressId(key).forEach(wideConsumer);
            case ORDER:
                this.wideOrderDao.findById(key).ifPresent(wideConsumer);
            case USER:
                this.wideOrderDao.findByUserId(key).forEach(wideConsumer);
        }
    }

    @Override
    public boolean supportUpdateFor(WideOrderType wideOrderType) {
        return false;
    }

    @Override
    public <KEY> void updateByItem(WideOrderType wideOrderType, KEY key, Consumer<WideOrder> wideConsumer) {
        Consumer<WideOrder> updateAndSave = wideConsumer.andThen(wideOrder -> wideOrderDao.save(wideOrder));
        doUpdateByItem(wideOrderType, (Long) key, updateAndSave);
    }

    @Override
    public <KEY> void updateByItem(WideOrderType wideOrderType, KEY key, WideItemData<WideOrderType, ?> item) {

    }
}
