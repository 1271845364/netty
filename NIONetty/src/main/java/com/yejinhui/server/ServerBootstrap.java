package com.yejinhui.server;

import com.yejinhui.server.pool.Boss;
import com.yejinhui.server.pool.NIOSelectorRunnablePool;

import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * 服务类
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 18:49
 */
public class ServerBootstrap {

    private NIOSelectorRunnablePool nioSelectorRunnablePool;

    public ServerBootstrap(NIOSelectorRunnablePool nioSelectorRunnablePool) {
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
    }

    /**
     * 绑定端口
     *
     * @param socketAddress
     */
    public void bind(final SocketAddress socketAddress) {
        try {
            //获取ServerSocketChannel通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            //设置为非阻塞的
            serverSocketChannel.configureBlocking(false);

            //绑定端口
            serverSocketChannel.socket().bind(socketAddress);

            //获取一个boss线程
            Boss nextBoss = nioSelectorRunnablePool.nextBoss();

            //向boss注册一个ServerSocketChannel通道
            nextBoss.registerAcceptChannelTask(serverSocketChannel);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
