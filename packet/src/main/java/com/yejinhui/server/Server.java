package com.yejinhui.server;

import com.yejinhui.common.codec.RequestDecoder;
import com.yejinhui.common.codec.ResponseEncoder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 10:24
 */
public class Server {

    public static void main(String[] args) {
        //netty服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //该线程池负责监听端口的
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        //负责channel数据读写，干活的
        ExecutorService workerExecutor = Executors.newCachedThreadPool();

        //设置一个NIOServerSocket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));

        //设置管道的工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                //从通道中获取管道
                ChannelPipeline pipeline = Channels.pipeline();
                //消息处理
                pipeline.addLast("myDecoder", new MyDecoder());
                pipeline.addLast("helloHandler", new HelloHandler());
                return pipeline;
            }
        });

        //设置参数
        serverBootstrap.setOption("backlog", 1024);
        serverBootstrap.setOption("tcpNoDelay", true);
        serverBootstrap.setOption("keepAlive", true);

        //绑定端口
        serverBootstrap.bind(new InetSocketAddress(10101));

        System.out.println("server start!!");
    }
}
