package com.yejinhui.client;

import com.yejinhui.client.scan.Invoker;
import com.yejinhui.client.scan.InvokerHolder;
import com.yejinhui.client.swing.Swingclient;
import com.yejinhui.common.core.model.Response;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author ye.jinhui
 * @description 消息接收处理类
 * @program netty-hello
 * @create 2020/3/21 16:24
 */
public class ClientHandler extends SimpleChannelHandler {

    /**
     * 界面
     */
    private Swingclient swingclient;

    public ClientHandler(Swingclient swingclient) {
        this.swingclient = swingclient;
    }

    /**
     * 接收消息
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        handlerResponse(response);
    }

    /**
     * 消息处理
     *
     * @param response
     */
    private void handlerResponse(Response response) {
        Invoker invoker = InvokerHolder.getInvoker(response.getModule(), response.getCmd());
        if (invoker != null) {
            try {
                invoker.invoke(response.getStateCode(), response.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //找不到执行器
            System.out.println(String.format("module:%s cmd:%s", response.getModule(), response.getCmd()));
        }
    }

    /**
     * 断开链接
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        swingclient.getTips().setText("与服务器断开链接~~~");
    }
}
