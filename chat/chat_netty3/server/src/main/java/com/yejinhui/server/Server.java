package com.yejinhui.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioSocketChannel;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 9:52
 */
@Component
public class Server {

    public void start() {
        //服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boss线程监听端口，worker线程服务数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置niosocket工厂

        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        //设置管道的工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("helloHandler", new ServerHandler());
                return pipeline;
            }
        });

        serverBootstrap.setOption("backlog", 1024);

        serverBootstrap.bind(new InetSocketAddress(10102));

        System.out.println("start!!!");
    }
}
