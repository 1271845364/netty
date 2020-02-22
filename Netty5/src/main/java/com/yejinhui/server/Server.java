package com.yejinhui.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * netty5服务端
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2020/2/22 19:20
 */
public class Server {

    public static void main(String[] args) {
        //服务端
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boss和worker
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //设置线程池
            serverBootstrap.group(boss, worker);

            //设置socket工厂
            serverBootstrap.channel(NioServerSocketChannel.class);

            //设置管道工厂
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new ServerHandler());
                }
            });

            //Netty3设置参数
//            serverBootstrap.setOption("backlog", 1024);
//            serverBootstrap.setOption("tcpNoDelay", true);
//            serverBootstrap.setOption("keepAlive", true);

            //设置参数，TCP参数
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 2048);//ServerSocketChannel的设置，链接缓冲池大小
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);//SocketChannel的设置，维持链接的活跃，清除一些死的链接
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);//SocketChannel的设置，关闭延迟发送

            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(10101);

            System.out.println("server start!");

            //等待服务端关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
