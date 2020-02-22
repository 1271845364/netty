package com.yejinhui.pool;

import java.nio.channels.SocketChannel;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 16:41
 */
public interface Worker {

    /**
     * 加入一个SocketChannel
     *
     * @param socketChannel
     */
    void registerNewChannelTask(SocketChannel socketChannel);
}
