package com.yejinhui.common.core.codec;

import com.yejinhui.common.core.model.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号  | 命令号  | 长度   |   数据  |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 16:11
 */
public class RequestEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Request request = (Request) msg;
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        //包头
        channelBuffer.writeInt(ConstantValue.HEADER_FLAG);
        //模块号
        channelBuffer.writeShort(request.getModule());
        //命令号
        channelBuffer.writeShort(request.getCmd());
        int length = request.getData() == null ? 0 : request.getData().length;
        //数据长度
        channelBuffer.writeInt(length);
        if (length > 0) {
            channelBuffer.writeBytes(request.getData());
        }
        return channelBuffer;
    }

}
