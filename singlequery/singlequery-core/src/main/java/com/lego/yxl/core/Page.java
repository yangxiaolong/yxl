package com.lego.yxl.core;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * 分页返回结果
 */
public class Page<T> {
    /**
     * 数据
     */
    private final List<T> content;
    /**
     * 分页信息
     */
    @Getter
    private final Pageable pageable;
    /**
     * 总的元素数量
     */
    private final long totalElements;

    public Page(List<T> content, Pageable pageable, long totalElements) {
        Preconditions.checkArgument(content != null);
        Preconditions.checkArgument(pageable != null);
        Preconditions.checkArgument(totalElements >= 0);
        this.content = content;
        this.pageable = pageable;
        this.totalElements = totalElements;
    }

    public static <R> Page<R> nullObject(Pageable pageable) {
        return new Page(Collections.emptyList(), pageable, 0);
    }

    /**
     * 当前页号
     * @return
     */
    public int getCurrentPage(){
        return this.pageable.getPageNo();
    }

    /**
     * 每页大小
     * @return
     */
    public int getPageSize(){
        return pageable.getPageSize();
    }

    /**
     * 返回数据
     * @return
     */
    public List<T> getContent(){
        return this.content;
    }

    /**
     * 总页数
     * @return
     */
    public int getTotalPages(){
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) getPageSize());
    }

    /**
     * 总元素数
     * @return
     */
    public long getTotalElements(){
        return this.totalElements;
    }

    /**
     * 是否有数据
     * @return
     */
    public boolean hasContent(){
        return !CollectionUtils.isEmpty(this.content);
    }

    /**
     * 是否为第一页
     * @return
     */
    public boolean isFirst(){
        return !hasPrevious();
    }

    /**
     * 是否为最后一页
     * @return
     */
    public boolean isLast(){
        return !hasNext();
    }

    /**
     * 是否有下一页
     * @return
     */
    public boolean hasNext(){
        return getCurrentPage() + 1 < getTotalPages();
    }

    /**
     * 是否有上一页
     * @return
     */
    public boolean hasPrevious(){
        return getCurrentPage() > 0;
    }

    public <R> Page<R> convert(Function<T, R> converter){
        if (CollectionUtils.isEmpty(getContent())){
            return new Page<>(Collections.emptyList(),
                    getPageable(),
                    getTotalElements());
        }

        List<R> results = content.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new Page<>(results, getPageable(), getTotalElements());
    }
}
