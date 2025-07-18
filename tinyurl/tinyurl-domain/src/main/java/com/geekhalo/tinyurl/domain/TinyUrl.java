package com.geekhalo.tinyurl.domain;

import com.google.common.base.Preconditions;
import com.lego.yxl.bitop.intop.IntMaskOp;
import com.lego.yxl.command.core.support.AbstractAggRoot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tiny_url")
@Data
@NoArgsConstructor
public class TinyUrl extends AbstractAggRoot {
    /**
     * 是否启动缓存，在第一次访问时，将数据同步到 redis 中
     */
    private static final IntMaskOp CACHE_ENABLE = IntMaskOp.MASK_1;

    /**
     * 是否启动缓存同步，在保存只好，将数据同步的 redis
     */
    private static final IntMaskOp CACHE_SYNC_ENABLE = IntMaskOp.MASK_2;

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TinyUrlType type;

    @Enumerated(EnumType.STRING)
    private TinyUrlStatus status;

    @Column(name = "url", updatable = false)
    private String url;

    @Column(name = "max_count", updatable = false)
    private Integer maxCount;

    @Column(name = "access_count")
    private Integer accessCount;

    @Column(name = "begin_time", updatable = false)
    private Date beginTime;

    @Column(name = "expire_time", updatable = false)
    private Date expireTime;

    @Column(name = "switch_code", updatable = false, nullable = false)
    private Integer switches = 0;

    @Override
    public Long getId() {
        return this.id;
    }

    public static TinyUrl createTinyUrl(CreateTinyUrlContext context) {
        TinyUrl tinyUrl = new TinyUrl();

        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.COMMON);
        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.init(context.getCommand());

        return tinyUrl;
    }


    public static TinyUrl createExpireTimeTinyUrl(CreateExpireTimeTinyUrlContext context) {
        TinyUrl tinyUrl = new TinyUrl();

        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.EXPIRE_TIME);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.setExpireTime(context.getCommand().getExpireTime());
        tinyUrl.setBeginTime(context.getCommand().parseBeginTime());

        tinyUrl.init(context.getCommand());

        return tinyUrl;
    }

    public static TinyUrl createLimitTimeTinyUrl(CreateLimitTimeTinyUrlContext context) {
        TinyUrl tinyUrl = new TinyUrl();

        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.LIMIT_TIME);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.setMaxCount(context.getCommand().getMaxCount());
        tinyUrl.setAccessCount(0);

        tinyUrl.init(context.getCommand());

        return tinyUrl;
    }

    private void init(AbstractCreateTinyUrlCommand command) {
        setStatus(TinyUrlStatus.ENABLE);
        if (command.getEnableCache() != null && command.getEnableCache()) {
            setEnableCache();
        }
        if (command.getEnableCacheSync() != null && command.getEnableCacheSync()) {
            setEnableCacheSync();
            setEnableCache();
        }
        addEvent(new TinyUrlCreatedEvent(this));
    }

    public void incrAccessCount(IncrAccessCountCommand command) {
        int incrCount = command.incrCount();

        incrAccessCount(incrCount);
    }

    public void incrAccessCount(int incrCount) {
        Integer accessCount = getAccessCount();
        if (getType().needUpdateAccessCount()) {
            Integer accessCountNew = accessCount + incrCount;
            setAccessCount(accessCountNew);
        }
    }

    public void disable(DisableTinyUrlCommand command) {
        if (getStatus() == TinyUrlStatus.ENABLE) {
            setStatus(TinyUrlStatus.DISABLE);
            addEvent(new TinyurlDisableEvent(this));
        }
    }

    @Override
    public void validate() {
        super.validate();
        Preconditions.checkArgument(getType() != null);
        Preconditions.checkArgument(getStatus() != null);
        Preconditions.checkArgument(StringUtils.isNotEmpty(getUrl()));
        this.type.validate(this);
    }

    public boolean canAccess() {
        return this.type.canAccess(this);
    }

    public boolean needUpdateAccessCount() {
        return this.type.needUpdateAccessCount();
    }


    boolean checkTime() {
        return new Date().after(getBeginTime()) && new Date().before(getExpireTime());
    }

    boolean checkCount() {
        return getAccessCount() < getMaxCount();
    }

    boolean checkStatus() {
        return TinyUrlStatus.ENABLE == getStatus();
    }

    private void setEnableCache() {
        setSwitches(CACHE_ENABLE.set(this.getSwitches(), true));
    }

    private void setEnableCacheSync() {
        setSwitches(CACHE_SYNC_ENABLE.set(this.getSwitches(), true));
    }

    public boolean isEnableCache() {
        return CACHE_ENABLE.isSet(this.switches);
    }

    public boolean isEnableCacheSync() {
        return CACHE_SYNC_ENABLE.isSet(this.switches);
    }
}
