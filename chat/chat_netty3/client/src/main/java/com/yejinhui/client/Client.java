package com.yejinhui.client;

import com.yejinhui.client.swing.Swingclient;
import com.yejinhui.common.core.model.Request;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 15:49
 */
@Component
public class Client {

    /**
     * 界面
     */
    @Autowired
    private Swingclient swingclient;

    /**
     * 服务类
     */
    ClientBootstrap bootstrap = new ClientBootstrap();

    /**
     * 会话
     */
    private Channel channel;

    /**
     * boss和worker线程池
     */
    private ExecutorService boss = Executors.newCachedThreadPool();
    private ExecutorService worker = Executors.newCachedThreadPool();

    @PostConstruct
    public void init() {
        //设置socket工厂
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

        //设置管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("hiHandler", new ClientHandler(swingclient));
                return pipeline;
            }
        });
    }

    /**
     * 链接
     *
     * @throws InterruptedException
     */
    public void connect() throws InterruptedException {
        //链接服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10102));
        connect.sync();
        channel = connect.getChannel();
    }

    /**
     * 关闭
     */
    public void shutdown() {
        bootstrap.shutdown();
    }

    /**
     * 获取会话
     *
     * @return
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * 发送消息
     *
     * @param request
     * @throws InterruptedException
     */
    public void sendRequest(Request request) throws InterruptedException {
        if (channel == null || !channel.isConnected()) {
            connect();
        }
        channel.write(request);
    }

}
