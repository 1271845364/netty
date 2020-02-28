package com.yejinhui.serial;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.nio.Buffer;
import java.util.Arrays;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 21:46
 */
public class Test3 {

    public static void main(String[] args) {
        //netty提供的动态大小的buffer(缓冲区)
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeInt(101);
        channelBuffer.writeDouble(80.1);

        //channelBuffer.writerIndex() 是写指针位置
        byte[] bytes = new byte[channelBuffer.writerIndex()];
        channelBuffer.readBytes(bytes);
        System.out.println(Arrays.toString(bytes));

        //反序列化
        ChannelBuffer channelBuffer1 = ChannelBuffers.wrappedBuffer(bytes);
        System.out.println(channelBuffer1.readInt());
        System.out.println(channelBuffer1.readDouble());
    }
}
