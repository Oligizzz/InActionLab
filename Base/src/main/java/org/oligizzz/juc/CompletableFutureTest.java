package org.oligizzz.juc;

import org.junit.Test;
import org.oligizzz.common.thread.ThreadPoolHolder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * @author KongXiao
 * @date 2022-07-22 20:36
 */
public class CompletableFutureTest {

    @Test
    public void supplyAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyRes = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "开始" + System.currentTimeMillis());

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (false) {
                throw new RuntimeException("测试异常");
            } else {
                System.out.println(Thread.currentThread() + "结束" + System.currentTimeMillis());
                return "执行结果";
            }

        }, ThreadPoolHolder.getCommonThreadPoll());
        System.out.println("主线程开始" + System.currentTimeMillis());
        System.out.println("Result:" + supplyRes.get());
        System.out.println("主线程结束" + System.currentTimeMillis());
    }

    @Test
    public void runAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runRes = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread() + "开始" + System.currentTimeMillis());

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (false) {
                throw new RuntimeException("测试异常");
            } else {
                System.out.println(Thread.currentThread() + "结束" + System.currentTimeMillis());
            }

        }, ThreadPoolHolder.getCommonThreadPoll());
        System.out.println("主线程开始" + System.currentTimeMillis());
        System.out.println("Result:" + runRes.get());
        System.out.println("主线程结束" + System.currentTimeMillis());
    }

    /**
     * CompletionStage接口串行测试
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void thenApplyTest() throws ExecutionException, InterruptedException {

        CompletableFuture<String> stepOne = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "开始执行" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "执行结束" + System.currentTimeMillis());
            return "执行结果";
        }, ThreadPoolHolder.getCommonThreadPoll());

        // 接受上一步返回值处理后返回新值
        CompletableFuture<String> stepTwo = stepOne.thenApply((result) -> {
            System.out.println(Thread.currentThread() + "thenApply开始执行" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "thenApply执行结束" + System.currentTimeMillis());
            return result + "+结果已处理【步骤二】";
        });

        // 接受上一步返回值处理后返回新值
        CompletableFuture<String> stepThree = stepTwo.thenApply((result) -> {
            System.out.println(Thread.currentThread() + "thenApply开始执行" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "thenApply执行结束" + System.currentTimeMillis());
            return result + "+结果已处理【步骤三】";
        });

        // 接受上一步返回值处理后返回新值，但是可以自定义线程池，可使用不同线程执行任务
        CompletableFuture<String> stepFour = stepThree.thenApplyAsync((result) -> {
            System.out.println(Thread.currentThread() + "thenApplyAsync开始执行" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "thenApplyAsync执行结束" + System.currentTimeMillis());
            return result + "+结果已处理【步骤四】";
        }, ThreadPoolHolder.getCommonThreadPoll());


        // 接受上一步返回值处理后返回没有返回值
        CompletableFuture<Void> stepFive = stepFour.thenAccept((result) -> {
            System.out.println(Thread.currentThread() + "thenAccept开始执行" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thenAccept");
            System.out.println(Thread.currentThread() + "thenAccept执行结束" + System.currentTimeMillis());
        });

        // 不接收值，也不返回值
        CompletableFuture<Void> stepSix = stepFive.thenRun(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("流程处理");
        });

        System.out.println("主线程开始" + System.currentTimeMillis());
        System.out.println("Result:" + stepTwo.get());
        System.out.println("Result:" + stepThree.get());
        System.out.println("Result:" + stepFour.get());
        System.out.println("主线程结束" + System.currentTimeMillis());
    }


}
