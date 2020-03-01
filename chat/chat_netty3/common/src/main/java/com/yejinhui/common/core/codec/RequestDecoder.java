package com.yejinhui.common.core.codec;

import com.yejinhui.common.core.model.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 数据包解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号  | 命令号  |  长度  |   数据   |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节
 * 命令号2字节
 * 长度4字节(数据部分占有字节数量)
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 15:52
 */
public class RequestDecoder extends FrameDecoder {

    /**
     * 数据包基本长度
     */
    private static final int BASE_LENGTH = 4 + 2 + 2 + 4;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() >= BASE_LENGTH) {
            int beginIndex;
            //找到包头
            while (true) {
                //包头开始游标点
                beginIndex = buffer.readerIndex();
                //标记初始游标位置
                buffer.markReaderIndex();
                if (buffer.readInt() == ConstantValue.HEADER_FLAG) {
                    break;
                }
                //未读到包头不能略过4个字节，这样有可能就丢了，只能一个字节一个字节的略过
                buffer.resetReaderIndex();
                buffer.readByte();

                //不满足
                if (buffer.readableBytes() < BASE_LENGTH) {
                    return null;
                }
            }
            //找到了包头，就可以读取数据了
            //模块号
            short module = buffer.readShort();
            //命令号
            short cmd = buffer.readShort();

            //读取数据长度
            int dataLength = buffer.readInt();
            if (dataLength < 0) {
                channel.close();
            }

            //数据还没到齐
            if (buffer.readableBytes() < dataLength) {
                buffer.readerIndex(beginIndex);
                return null;
            }

            //读取数据部分
            byte[] bytes = new byte[dataLength];
            buffer.readBytes(bytes);

            //放入到request对象中，继续往后面的handler传递
            Request request = Request.valueOf(module, cmd, bytes);
            return request;
        }
        //数据不完整，等待完整的数据包
        return null;
    }
}
