package com.yejinhui.server;

import com.yejinhui.common.constant.StateCode;
import com.yejinhui.common.fuben.request.FightRequest;
import com.yejinhui.common.fuben.response.FightResponse;
import com.yejinhui.common.model.Request;
import com.yejinhui.common.model.Response;
import com.yejinhui.spring.Invoker;
import com.yejinhui.spring.InvokerHolder;
import org.jboss.netty.channel.*;

/**
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/25 10:34
 */
public class HelloHandler extends SimpleChannelHandler {

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
        //用户上线了，做一些初始化缓存的工作
        //如果在黑名单就不允许链接了，防止恶意攻击，降低服务器压力，直接给调用channelClose(ctx,e);
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
        //用户下线了，可以清除缓存
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
        //1s发送300条消息是不是列入黑名单？

        System.out.println(ctx.getChannel() + ":messageReceived");
        Request request = (Request) e.getMessage();
        System.out.println(ctx.getChannel() + ":" + request.toString());

        //回写数据，下面这样写主要是解决业务和技术耦合度太高的问题
        Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());
        invoker.invoke(null);

        ctx.getChannel().close();

        super.messageReceived(ctx, e);
    }
}
