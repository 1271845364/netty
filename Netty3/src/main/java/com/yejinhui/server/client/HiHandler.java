package com.yejinhui.server.client;

import org.jboss.netty.channel.*;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 15:09
 */
public class HiHandler extends SimpleChannelHandler {

    /**
     * 通道关闭
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(ctx.getChannel() + ":channelClosed");
        super.channelClosed(ctx, e);
    }

    /**
     * 新连接
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(ctx.getChannel() + ":channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 通道断开链接：必须是已经建立链接，关闭通道的时候才会触发
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(ctx.getChannel() + ":channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * 捕获异常
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println(ctx.getChannel() + ":exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 消息接收
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(ctx.getChannel() + ":" + e.getMessage());
        super.messageReceived(ctx, e);
    }

}
