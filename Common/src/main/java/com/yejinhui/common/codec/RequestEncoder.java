package com.yejinhui.common.codec;

import com.yejinhui.common.constant.ConstantValue;
import com.yejinhui.common.model.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 请求编码
 *
 * 包头 模块号 命令号 数据长度  数据
 *
 * 包头4个字节
 * 模块号2个字节
 * 命令号2个字节
 * 数据长度4个字节
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/28 22:17
 */
public class RequestEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Request request = (Request) msg;
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        //往buffer写数据
        //写包头
        channelBuffer.writeInt(ConstantValue.FLAG);
        channelBuffer.writeShort(request.getModule());
        channelBuffer.writeShort(request.getCmd());
        channelBuffer.writeInt(request.getDataLength());
        if (request.getData() != null) {
            channelBuffer.writeBytes(request.getData());
        }
        return channelBuffer;
    }
}
