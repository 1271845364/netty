package com.yejinhui.server;

import com.yejinhui.server.pool.Boss;
import com.yejinhui.server.pool.NIOSelectorRunnablePool;
import com.yejinhui.server.pool.Worker;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * boss实现类，监听端口
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 16:52
 */
public class NIOServerBoss extends AbstractNIOSelector implements Boss {

    public NIOServerBoss(Executor executor, String threadName, NIOSelectorRunnablePool nioSelectorRunnablePool) {
        super(executor, threadName, nioSelectorRunnablePool);
    }

    /**
     * 处理
     *
     * @param selector
     * @throws IOException
     */
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        if(selectionKeys.isEmpty()) {
            return;
        }

        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while(iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            iterator.remove();
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            //新的客户端
            SocketChannel socketChannel = serverSocketChannel.accept();
            //设置为非阻塞的
            socketChannel.configureBlocking(false);
            //获取一个worker
            Worker nextWorker = this.getNIOSelectorRunnablePool().nextWorker();
            //注册新客户端接入任务
            nextWorker.registerNewChannelTask(socketChannel);

            System.out.println("新客户端链接");
        }
    }

    public void registerAcceptChannelTask(final ServerSocketChannel serverSocketChannel) {
        final Selector selector = this.selector;
        registerTask(new Runnable() {
            public void run() {
                try {
                    //注册serverSocketChannel到selector上
                    serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected int select(Selector selector) throws IOException {
        return selector.select();
    }

}
