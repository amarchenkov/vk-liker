package com.github.vk.bot.common;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * Created at 16.10.2017 16:28
 *
 * @author AMarchenkov
 */
public interface ForkJoinMicroService {

    String getWorkerNamePrefix();

    @Bean
    default ForkJoinPool forkJoinPool() {
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName(getWorkerNamePrefix() + "-" + worker.getPoolIndex());
            return worker;
        };
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), factory, null, true);
    }
}
