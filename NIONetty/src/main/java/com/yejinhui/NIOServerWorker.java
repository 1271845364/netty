package com.yejinhui;

import com.yejinhui.pool.NIOSelectorRunnablePool;
import com.yejinhui.pool.Worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * worker实现类，为客户端服务，进行读写任务的
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 17:51
 */
public class NIOServerWorker extends AbstractNIOSelector implements Worker {

    public NIOServerWorker(Executor executor, String threadName, NIOSelectorRunnablePool nioSelectorRunnablePool) {
        super(executor, threadName, nioSelectorRunnablePool);
    }

    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        if (selectionKeys.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            //移除，防止重复处理
            iterator.remove();

            //得到事件发生的通道
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

            //数据总长度
            int ret = 0;
            boolean failure = true;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //读取数据
            try {
                ret = socketChannel.read(byteBuffer);
                failure = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            //判断是否链接断开
            if (ret <= 0 || failure) {
                selectionKey.cancel();
                System.out.println("客户端断开链接");
            } else {
                System.out.println("收到数据：" + new String(byteBuffer.array()));

                //回写数据
                ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
                //将消息发送给客户端
                socketChannel.write(outBuffer);
            }

        }
    }

    /**
     * 加入一个新的socket客户端
     *
     * @param socketChannel
     */
    public void registerNewChannelTask(final SocketChannel socketChannel) {
        final Selector selector = this.selector;
        registerTask(new Runnable() {
            public void run() {
                try {
                    //将客户端注册到selector上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected int select(Selector selector) throws IOException {
        return selector.select(500);
    }
}
