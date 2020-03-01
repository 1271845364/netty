package com.yejinhui.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 10:34
 */
public class HelloHandler extends SimpleChannelHandler {

    private int count = 1;

    /**
     * 消息接收
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(ctx.getChannel() + ":" + e.getMessage() + "=" + count++);
    }

}
