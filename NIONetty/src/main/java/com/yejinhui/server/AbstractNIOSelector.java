package com.yejinhui.server;

import com.yejinhui.server.pool.NIOSelectorRunnablePool;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象selector线程类
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 16:53
 */
public abstract class AbstractNIOSelector implements Runnable {

    /**
     * 线程池
     */
    private final Executor executor;

    /**
     * 选择器
     */
    protected Selector selector;

    /**
     * 选择器wakenUp状态标记
     */
    protected final AtomicBoolean wakeUp = new AtomicBoolean();

    /**
     * 任务队列
     */
    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 线程管理对象
     */
    protected NIOSelectorRunnablePool nioSelectorRunnablePool;

    public AbstractNIOSelector(Executor executor, String threadName, NIOSelectorRunnablePool nioSelectorRunnablePool) {
        this.executor = executor;
        this.threadName = threadName;
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
        openSelector();
    }

    /**
     * 获取selector并启动线程
     */
    private void openSelector() {
        try {
            this.selector = Selector.open();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create a selector.");
        }
        executor.execute(this);
    }

    public void run() {
        Thread.currentThread().setName(this.threadName);
        while (true) {
            try {
                wakeUp.set(false);

                select(selector);

                processTaskQueue();

                process(selector);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 注册一个任务并激活selector
     *
     * @param task
     */
    protected final void registerTask(Runnable task) {
        taskQueue.add(task);
        Selector selector = this.selector;

        if (selector != null) {
            if (wakeUp.compareAndSet(false, true)) {
                selector.wakeup();
            }
        } else {
            taskQueue.remove(task);
        }
    }

    /**
     * 执行队列里的任务
     */
    private void processTaskQueue() {
        for (; ; ) {
            final Runnable task = taskQueue.poll();
            if (task == null) {
                break;
            }
            task.run();
        }
    }

    /**
     * 获取线程管理对象
     *
     * @return
     */
    public NIOSelectorRunnablePool getNIOSelectorRunnablePool() {
        return nioSelectorRunnablePool;
    }

    /**
     * select抽象方法
     *
     * @param selector
     * @return
     * @throws IOException
     */
    protected abstract int select(Selector selector) throws IOException;

    /**
     * selector的业务处理
     *
     * @param selector
     * @throws IOException
     */
    protected abstract void process(Selector selector) throws IOException;

}
