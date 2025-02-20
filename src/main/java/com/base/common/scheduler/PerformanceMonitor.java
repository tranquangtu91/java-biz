package com.base.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PerformanceMonitor {
    Runtime instance;
    long freeMemory;
    long oldFreeMemory;
    long totalMemory;

    PerformanceMonitor() {
        instance = Runtime.getRuntime();
        freeMemory = (long) (instance.freeMemory() / 1048576L);
        oldFreeMemory = freeMemory;
        totalMemory = 0L;
    }

    @Scheduled(cron = "0/30 * * ? * *")
    void print() {
        freeMemory = (long) (instance.freeMemory() / 1048576L);
        totalMemory = (long) (instance.totalMemory() / 1048576L);
        final String msg = String.format(
                "Total Memory: %d MB - In Use Memory: %d MB (%d MB) - Max Memory: %d MB",
                totalMemory, totalMemory - freeMemory, freeMemory - oldFreeMemory, instance.maxMemory() / 1048576L);
        log.info(msg);
        oldFreeMemory = freeMemory;
        System.gc();
    }
}
