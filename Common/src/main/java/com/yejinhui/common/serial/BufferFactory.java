package com.yejinhui.common.serial;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.nio.ByteOrder;

/**
 * buff工厂
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/24 22:52
 */
public class BufferFactory {

    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    /**
     * 获取一个buffer
     *
     * @return
     */
    public static ChannelBuffer getBuffer() {
        return ChannelBuffers.dynamicBuffer();
    }

    /**
     * 将数据写入buffer
     *
     * @param bytes
     * @return
     */
    public static ChannelBuffer getBuffer(byte[] bytes) {
        return ChannelBuffers.copiedBuffer(bytes);
    }

}
