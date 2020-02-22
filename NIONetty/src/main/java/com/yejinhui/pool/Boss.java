package com.yejinhui.pool;

import java.nio.channels.ServerSocketChannel;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 16:39
 */
public interface Boss {

    /**
     * 加入一个ServerSocketChannel
     *
     * @param serverSocketChannel
     */
    void registerAcceptChannelTask(ServerSocketChannel serverSocketChannel);

}
