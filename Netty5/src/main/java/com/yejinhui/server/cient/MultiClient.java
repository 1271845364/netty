package com.yejinhui.server.cient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多连接的单客户端
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 10:06
 */
public class MultiClient {

    /**
     * 客户端服务类
     */
    private Bootstrap bootstrap = new Bootstrap();

    /**
     * 缓存链接
     */
    private List<Channel> channels = new ArrayList<Channel>();

    /**
     * 引用计数器
     */
    private final AtomicInteger index = new AtomicInteger();

    /**
     * 初始化
     *
     * @param count
     */
    public void init(int count) {
        //worker
        EventLoopGroup worker = new NioEventLoopGroup();

        //设置线程池
        bootstrap.group(worker);

        //设置socket工厂
        bootstrap.channel(NioSocketChannel.class);

        //设置管道
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });

        for (int i = 1; i <= count; i++) {
            //获取链接
            ChannelFuture channelFuture = bootstrap.connect("10.213.192.40", 10101);
            channels.add(channelFuture.channel());
        }

    }

    /**
     * 获取链接
     *
     * @return
     */
    public Channel nextChannel() {
        return getFirstActiveChannel(0);
    }

    /**
     * @param count
     * @return
     */
    private Channel getFirstActiveChannel(int count) {
        Channel channel = channels.get(Math.abs(index.getAndIncrement() % channels.size()));
        if (!channel.isActive()) {
            //重新连接
            this.reconnect(channel);
            if (count >= channels.size()) {
                throw new RuntimeException("no can use channel");
            }
            return getFirstActiveChannel(count + 1);
        }
        return channel;
    }

    /**
     * 重连
     *
     * @param channel
     */
    private void reconnect(Channel channel) {
        //防止多个同时重连
        synchronized (channel) {
            if (channels.indexOf(channel) == -1) {
                //channel不在channels中，说明重连过
                return;
            }
            Channel newChannel = bootstrap.connect("10.213.192.40", 10101).channel();
            channels.set(channels.indexOf(channel), newChannel);
        }
    }

}
