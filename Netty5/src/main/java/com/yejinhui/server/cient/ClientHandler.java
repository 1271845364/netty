package com.yejinhui.server.cient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2020/2/22 22:50
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel() + ":" + msg);
    }
}
