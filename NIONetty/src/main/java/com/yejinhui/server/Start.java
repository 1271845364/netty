package com.yejinhui.server;

import com.yejinhui.server.pool.NIOSelectorRunnablePool;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 启动类
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 18:55
 */
public class Start {

    public static void main(String[] args) {
        //初始化线程池
        NIOSelectorRunnablePool nioSelectorRunnablePool =
                new NIOSelectorRunnablePool(Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool());

        //获取服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap(nioSelectorRunnablePool);

        //绑定端口
        serverBootstrap.bind(new InetSocketAddress(10101));
        System.out.println("server start");
    }

}
