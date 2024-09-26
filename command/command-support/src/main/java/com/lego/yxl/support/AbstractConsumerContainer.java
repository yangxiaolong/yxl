package com.lego.yxl.support;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;


@Slf4j
public abstract class AbstractConsumerContainer implements ConsumerContainer, InitializingBean, SmartLifecycle {
    protected final Environment environment;
    protected final Object bean;
    private boolean running;
    private DefaultMQPushConsumer consumer;

    @Getter
    @Setter
    private int delayLevelWhenNextConsume = 1;

    public AbstractConsumerContainer(Environment environment, Object bean) {
        this.environment = environment;
        this.bean = bean;
    }

    protected String resolve(String value) {
        if (StringUtils.hasText(value)) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }

    protected boolean skipWhenException() {
        return this.environment.getProperty("async.consumer.skipWhenError", Boolean.TYPE, false);
    }

    protected void doStart() {
        try {
            this.consumer.start();
            log.info("success to start consumer {}", this.consumer);
        } catch (MQClientException e) {
            log.error("failed to start rocketmq consumer {}", this.consumer);
        }
    }

    protected void doShutdown() {
        this.consumer.shutdown();
        log.info("success to shutdown consumer {}", this.consumer);
    }

    protected abstract DefaultMQPushConsumer createConsumer() throws Exception;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 构建 DefaultMQPushConsumer
        this.consumer = createConsumer();
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        stop();
    }

    @Override
    public void start() {
        if (this.running) {
            return;
        }

        doStart();
        this.running = true;
    }

    @Override
    public void stop() {
        this.running = false;
        doShutdown();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Object getBean() {
        return this.bean;
    }

    public int getDelayLevelWhenNextConsume() {
        return this.delayLevelWhenNextConsume;
    }

    public void setDelayLevelWhenNextConsume(int delayLevelWhenNextConsume) {
        this.delayLevelWhenNextConsume = delayLevelWhenNextConsume;
    }
}
