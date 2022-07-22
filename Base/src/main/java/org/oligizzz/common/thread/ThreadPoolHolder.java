package org.oligizzz.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author KongXiao
 * @date 2022-07-22 20:36
 */
public class ThreadPoolHolder {

    private static final int CORE_POOL_SIZE = 200;
    private static final int MAXIMUM_POOL_SIZE = 300;
    private static final long MAX_KEEP_ALIVE = 300;
    private static final int QUEUE_SIZE = 200;

    private static final ExecutorService COMMON_THREAD_POLL;
    private static final ThreadFactory THREAD_FACTORY;


    static {
        THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("BASE通用线程池—线程编号：%d").build();
        COMMON_THREAD_POLL = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                MAX_KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
                THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy());
    }


    public static ExecutorService getCommonThreadPoll() {
        return COMMON_THREAD_POLL;
    }
}
