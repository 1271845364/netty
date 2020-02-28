package com.yejinhui.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 服务端消息处理
 * <p>
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
     * 接收事件的
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            //读和写都超时了
            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                ChannelFuture channelFuture = ctx.writeAndFlush("time out,you will close");
                channelFuture.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        ctx.channel().close();
                    }
                });
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
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
