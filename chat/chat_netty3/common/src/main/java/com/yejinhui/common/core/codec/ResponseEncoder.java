package com.yejinhui.common.core.codec;

import com.yejinhui.common.core.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * |  包头	|  模块号  | 命令号  | 结果码 |  长度   |  数据  |
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * </pre>
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 20:20
 */
public class ResponseEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Response response = (Response) msg;
        System.out.println(response.toString());
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        //写出包头
        channelBuffer.writeInt(ConstantValue.HEADER_FLAG);
        channelBuffer.writeShort(response.getModule());
        channelBuffer.writeShort(response.getCmd());
        channelBuffer.writeInt(response.getStateCode());
        int length = response.getData() == null ? 0 : response.getData().length;
        channelBuffer.writeInt(length);
        if (length > 0) {
            channelBuffer.writeBytes(response.getData());
        }
        return channelBuffer;
    }
}
