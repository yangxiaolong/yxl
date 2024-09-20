package com.lego.yxl.executor.items;

import com.lego.yxl.executor.item.JoinItemExecutor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 并行执行器，同一 level 的 join 在线程中并行执行
 */
@Slf4j
public class ParallelJoinItemsExecutor<DATA> extends AbstractJoinItemsExecutor<DATA> {

    private final ExecutorService executor;
    private final List<JoinExecutorWithLevel> joinExecutorWithLevel;

    public ParallelJoinItemsExecutor(Class<DATA> dataCls,
                                     List<JoinItemExecutor<DATA>> joinItemExecutors,
                                     ExecutorService executor) {
        super(dataCls, joinItemExecutors);
        this.executor = executor;
        this.joinExecutorWithLevel = buildJoinExecutorWithLevel();
    }

    private List<JoinExecutorWithLevel> buildJoinExecutorWithLevel() {
        // 根据 level 进行排序，解决依赖问题
        return getJoinItemExecutors()
                .stream()
                .collect(Collectors.groupingBy(JoinItemExecutor::runOnLevel))
                .entrySet()
                .stream()
                .map(entry -> new JoinExecutorWithLevel(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(o -> o.level))
                .collect(Collectors.toList());
    }

    @Override
    public void execute(List<DATA> datas) {
        this.joinExecutorWithLevel.forEach(joinExecutorWithLevel1 -> {
            log.debug("run join on level {} use {}", joinExecutorWithLevel1.getLevel(),
                    joinExecutorWithLevel1.getJoinItemExecutors());

            List<Task> tasks = buildTasks(joinExecutorWithLevel1, datas);
            try {
                if (log.isDebugEnabled()) {
                    StopWatch stopWatch = StopWatch.createStarted();
                    this.executor.invokeAll(tasks);
                    stopWatch.stop();

                    log.debug("run execute cost {} ms, task is {}.",
                            stopWatch.getTime(TimeUnit.MILLISECONDS),
                            tasks);
                } else {
                    this.executor.invokeAll(tasks);
                }
            } catch (InterruptedException e) {
                log.error("invoke task {} interrupted", tasks, e);
            }
        });
    }

    private List<Task> buildTasks(JoinExecutorWithLevel joinExecutorWithLevel, List<DATA> datas) {
        return joinExecutorWithLevel.getJoinItemExecutors()
                .stream()
                .map(joinExecutor -> new Task(joinExecutor, datas))
                .collect(Collectors.toList());
    }

    @Value
    class Task implements Callable<Void> {
        JoinItemExecutor<DATA> joinItemExecutor;
        List<DATA> datas;

        @Override
        public Void call() {
            this.joinItemExecutor.execute(this.datas);
            return null;
        }
    }

    @Value
    class JoinExecutorWithLevel {
        Integer level;
        List<JoinItemExecutor<DATA>> joinItemExecutors;
    }

}
