package com.yejinhui.server.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 泛型为啥是String？
 * 编解码器都是String
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2020/2/22 19:42
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel() + "：" + msg);

        //下面两行一样，底层调用的是同一个方法
//        ctx.channel().writeAndFlush("hi");
        ctx.writeAndFlush("hi");
    }

    /**
     * 新客户端链接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel() + ":channelActive");
    }

    /**
     * 客户端断开链接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel() + ":channelInactive");
    }

    /**
     * 异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel() + ":exceptionCaught");
        cause.printStackTrace();
    }

}
