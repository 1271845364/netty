package com.yejinhui.common.core.serial;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * Buffer工厂
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 17:25
 */
public class BufferFactory {

    /**
     * 获取一个buffer
     */
    public static ChannelBuffer getBuffer() {
        return ChannelBuffers.dynamicBuffer();
    }

    /**
     * 将数据写入buffer
     */
    public static ChannelBuffer getBuffer(byte[] data) {
        return ChannelBuffers.copiedBuffer(data);
    }

}
