package com.yejinhui.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.UpstreamMessageEvent;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 16:18
 */
public class MyHandler1 extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
        String message = new String(channelBuffer.array());
        System.out.println("handler1:" + message);

        //发送event给下一个upStream进行处理，handler之间的传递消息是通过这个方法sendUpstream()
        ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(),message,e.getRemoteAddress()));
        ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(),"abc",e.getRemoteAddress()));
    }
}
