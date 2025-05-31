package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.SmartLifecycle;

import java.util.concurrent.*;

@Slf4j
public class QueueBasedAsyncNumberGenerator
    implements NumberGenerator, SmartLifecycle {
    private final NumberGenerator numberGenerator;
    private final BlockingQueue<Long> blockingQueue;
    private final ExecutorService executorService;

    private boolean isRunning = false;

    public QueueBasedAsyncNumberGenerator(NumberGenerator numberGenerator,
                                          Integer blockingQueueSize) {
        this.numberGenerator = numberGenerator;
        this.blockingQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        this.executorService = createExecutorService();
    }

    private ExecutorService createExecutorService() {
        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("Queue-Async-Number-Generator-Thread-%d")
                .daemon(true)
                .uncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.error("failed to run task on thread {}", t, e);
                    }
                })
                .build();
        ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory);
        return executorService;
    }

    @SneakyThrows
    @Override
    public Long nextNumber() {
        return blockingQueue.take();
    }

    @Override
    public void start() {
        this.isRunning = true;
        this.executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    Long number = numberGenerator.nextNumber();
                    blockingQueue.put(number);
                }
            }
        });
    }

    @SneakyThrows
    @Override
    public void stop() {
        this.isRunning = false;
        this.executorService.shutdown();
        this.executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
