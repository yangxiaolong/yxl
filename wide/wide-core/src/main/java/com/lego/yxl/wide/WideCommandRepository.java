package com.lego.yxl.wide;

import java.util.List;
import java.util.function.Consumer;

public interface WideCommandRepository<
        ID,
        TYPE extends Enum<TYPE> & WideItemType<TYPE>,
        W extends Wide<ID, TYPE>> {
    void save(List<W> wides);

    List<W> findByIds(List<ID> masterIds);

    <KEY> void consumeByItem(TYPE type, KEY key, Consumer<W> wideConsumer);

    boolean supportUpdateFor(TYPE type);

    <KEY> void updateByItem(TYPE type, KEY key, Consumer<W> wideConsumer);

    <KEY> void updateByItem(TYPE type, KEY key, WideItemData<TYPE, ?> item);

}
