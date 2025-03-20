package com.base.common.application.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.base.common.application.utils.Closure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolUtils {

    /**
     * 
     * @param <T>
     * @param poolSize
     * @param items
     * @param func
     * @param timeout
     * @param unit     Mặc định MILLISECONDS
     * @return
     */
    @SuppressWarnings("unchecked")
    static <T> List<Future<T>> submit(Integer poolSize, List<T> items, Closure func, long timeout, TimeUnit unit) {
        CountDownLatch latch = new CountDownLatch(items.size());
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        List<Future<T>> futures = new ArrayList<>();
        for (T item : items) {
            Future<T> future = (Future<T>) executorService.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        func.handler(item);
                    } catch (Exception ex) {
                        throw ex;
                    } finally {
                        latch.countDown();
                    }
                }

            });

            futures.add(future);
        }

        try {
            if (timeout > 0) {
                if (unit == null)
                    unit = TimeUnit.MILLISECONDS;
                latch.await(timeout, unit);
            } else {
                latch.await();
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }

        executorService.shutdown();
        return futures;
    }
}
