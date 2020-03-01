package com.yejinhui.client;

import com.yejinhui.common.codec.RequestEncoder;
import com.yejinhui.common.codec.ResponseDecoder;
import com.yejinhui.common.fuben.request.FightRequest;
import com.yejinhui.common.model.Request;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty客户端，如果netty服务端不启动，直接启动客户端，是不会建立链接的，这时候只会调用HiHandler中的channelClosed()，
 * 而不会调用channelDisconnected()，因为根本没有建立链接
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 15:05
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        //客户端服务类
        ClientBootstrap clientBootstrap = new ClientBootstrap();

        //线程池
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();

        //设置socket工厂
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(bossExecutor, workerExecutor));

        //设置管道工厂
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                //要往管道中设置过滤器
                pipeline.addLast("decoder", new ResponseDecoder());
                pipeline.addLast("encoder", new RequestEncoder());
                //消息处理
                pipeline.addLast("hiHandler", new HiHandler());
                return pipeline;
            }
        });

        //链接服务端
        ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
        //通道就是会话，下面的Channel适合HiHandler方法中ctx.getChannel()是同一个
        Channel channel = channelFuture.sync().getChannel();
        System.out.println(channel + ":client start");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入");
            FightRequest fightRequest = new FightRequest();
            fightRequest.setFubenId(scanner.nextInt());
            fightRequest.setCount(scanner.nextInt());

            Request request = new Request();
            request.setModule((short) 1);
            request.setCmd((short) 2);
            request.setData(fightRequest.getBytes());
            //写出请求对象
            channel.write(request);
        }
    }
}
