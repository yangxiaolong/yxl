package com.lego.yxl.joininmemory.core.factory.items;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lego.yxl.joininmemory.core.annotation.JoinInMemeoryExecutorType;
import com.lego.yxl.joininmemory.core.annotation.JoinInMemoryConfig;
import com.lego.yxl.joininmemory.core.executor.item.JoinItemExecutor;
import com.lego.yxl.joininmemory.core.executor.items.JoinItemsExecutor;
import com.lego.yxl.joininmemory.core.executor.items.ParallelJoinItemsExecutor;
import com.lego.yxl.joininmemory.core.executor.items.SerialJoinItemsExecutor;
import com.lego.yxl.joininmemory.core.factory.item.JoinItemExecutorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 1. 使用 JoinItemExecutorFactory 为每个 Join 数据 创建 JoinItemExecutor <br />
 * 2. 将一个 class 的 JoinItemExecutor 封装成 JoinItemsExecutor
 */
@Slf4j
public class DefaultJoinItemsExecutorFactory implements JoinItemsExecutorFactory {
    private final List<JoinItemExecutorFactory> joinItemExecutorFactories;
    private final Map<String, ExecutorService> executorServiceMap;

    public DefaultJoinItemsExecutorFactory(Collection<? extends JoinItemExecutorFactory> joinItemExecutorFactories,
                                           Map<String, ExecutorService> executorServiceMap) {
        this.joinItemExecutorFactories = Lists.newArrayList(joinItemExecutorFactories);

        // 按执行顺序进行排序
        AnnotationAwareOrderComparator.sort(this.joinItemExecutorFactories);
        this.executorServiceMap = executorServiceMap;
    }

    @Override
    public <D> JoinItemsExecutor<D> createFor(Class<D> cls) {
        // 依次遍历 JoinItemExecutorFactory， 收集 JoinItemExecutor 信息
        List<JoinItemExecutor<D>> joinItemExecutors = this.joinItemExecutorFactories.stream()
                .flatMap(factory -> factory.createForType(cls).stream())
                .collect(Collectors.toList());

        // 从 class 上读取配置信息
        JoinInMemoryConfig joinInMemoryConfig = cls.getAnnotation(JoinInMemoryConfig.class);

        // 封装为 JoinItemsExecutor
        return buildJoinItemsExecutor(cls, joinInMemoryConfig, joinItemExecutors);
    }

    private <D> JoinItemsExecutor<D> buildJoinItemsExecutor(
            Class<D> cls, JoinInMemoryConfig joinInMemoryConfig, List<JoinItemExecutor<D>> joinItemExecutors) {
        // 使用 串行执行器
        if (joinInMemoryConfig == null || joinInMemoryConfig.executorType() == JoinInMemeoryExecutorType.SERIAL) {
            // 为什么log.info  无法识别?
            log.info("JoinInMemory for {} use serial executor", cls);
            return new SerialJoinItemsExecutor<>(cls, joinItemExecutors);
        }

        // 使用 并行执行器
        if (joinInMemoryConfig.executorType() == JoinInMemeoryExecutorType.PARALLEL) {
            log.info("JoinInMemory for {} use parallel executor, pool is {}", cls, joinInMemoryConfig.executorName());
            ExecutorService executor = executorServiceMap.get(joinInMemoryConfig.executorName());
            Preconditions.checkArgument(executor != null);
            return new ParallelJoinItemsExecutor<>(cls, joinItemExecutors, executor);
        }
        throw new IllegalArgumentException();
    }
}
