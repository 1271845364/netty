package com.yejinhui.server;

import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 12:37
 */
public class HelloHandler extends /*IdleStateAwareChannelHandler*/ SimpleChannelHandler {

    //    @Override
//    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(ctx.getChannel() + ":" + e.getState() + ":" + simpleDateFormat.format(new Date()));
//    }
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
            throws Exception {
        if (e instanceof IdleStateEvent) {
            //到了指定时间，没有读也没有写就关闭会话
            if (((IdleStateEvent) e).getState() == IdleState.ALL_IDLE) {
                System.out.println("用户下线");
                Channel channel = ctx.getChannel();
                ChannelFuture channelFuture = channel.write("time out,you will close");
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        channel.close();
                    }
                });

            }

//            IdleStateEvent idleStateEvent = (IdleStateEvent) e;
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println(ctx.getChannel() + ":" + idleStateEvent.getState() + ":" + simpleDateFormat.format(new Date()));
        } else {
            //继续往下调用
            super.handleUpstream(ctx, e);
        }
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
        ctx.getChannel().write("hi");
    }

}
