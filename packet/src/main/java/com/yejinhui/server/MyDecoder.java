package com.yejinhui.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 17:19
 */
public class MyDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        //4为存储数据长度的int值所占的字节，只有大于这个，才去进行读取数据
        if (buffer.readableBytes() > 4) {
            //标记一下，如果读出现问题，可以回来
            buffer.markReaderIndex();

            int dataLength = buffer.readInt();
            //
            if (buffer.readableBytes() < dataLength) {
                //重置【回滚】上一次的markReaderIndex
                buffer.resetReaderIndex();
                //缓存当前剩余的buffer数据，等待剩下的数据包到来
                return null;
            }
            //没问题正常读数据，只读dataLength个字节数据，可能buffer中还有数据，就是粘包了，应该递归调用的，但是FrameDecoder.java中给解决了
            byte[] dst = new byte[dataLength];
            buffer.readBytes(dst);
            //往下传递对象
            return new String(dst);
        }
        //缓存当前剩余的buffer数据，等待剩下的数据包到来
        return null;
    }

}
