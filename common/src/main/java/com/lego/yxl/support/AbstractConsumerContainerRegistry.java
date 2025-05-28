package com.lego.yxl.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class AbstractConsumerContainerRegistry implements BeanPostProcessor, SmartLifecycle {

    @Getter(AccessLevel.PROTECTED)
    private final List<ConsumerContainer> consumerContainers = Lists.newArrayList();

    @Getter(AccessLevel.PROTECTED)
    private final Environment environment;

    private boolean running;

    public AbstractConsumerContainerRegistry(Environment environment) {
        Preconditions.checkArgument(environment != null);
        this.environment = environment;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(@Nonnull Runnable runnable) {
        stop();
    }

    @Override
    public void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        this.consumerContainers.forEach(ConsumerContainer::start);
    }

    @Override
    public void stop() {
        if (!this.running) {
            return;
        }

        this.running = false;
        this.consumerContainers.forEach(ConsumerContainer::stop);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    /**
     * 是否为 激活 的 Profile
     *
     * @param consumerProfile
     * @return
     */
    protected boolean isActiveProfile(String consumerProfile) {
        return StringUtils.isEmpty(consumerProfile)
                || Arrays.asList(getEnvironment().getActiveProfiles()).contains(consumerProfile);
    }

}
