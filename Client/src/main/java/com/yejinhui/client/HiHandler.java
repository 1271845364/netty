package com.yejinhui.client;

import com.yejinhui.common.fuben.response.FightResponse;
import com.yejinhui.common.model.Response;
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
        Response reseponse = (Response) e.getMessage();
        System.out.println(ctx.getChannel() + ":" + e.getMessage());

        //在handler中写这么多业务，如果有很多业务模块，这会很乱；下面会通过写个注解来重构，转发给对应的业务层去完成
        if (reseponse.getModule() == 1) {
            if (reseponse.getCmd() == 1) {
                FightResponse fightResponse = new FightResponse();
                fightResponse.readFromBytes(reseponse.getData());
                System.out.println(fightResponse.toString());
            } else if (reseponse.getCmd() == 2) {

            }
        } else if (reseponse.getModule() == 2) {

        }
        super.messageReceived(ctx, e);
    }

}
