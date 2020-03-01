package com.yejinhui.common.core.codec;

import com.yejinhui.common.core.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import sun.reflect.generics.tree.ByteSignature;
import sun.reflect.generics.tree.ReturnType;

/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * |  包头	|  模块号  | 命令号 |  结果码 |  长度   |   数据  |
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * </pre>
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 16:17
 */
public class ResponseDecoder extends FrameDecoder {

    /**
     * 数据包基本长度
     */
    private static final int BASE_LENGTH = 4 + 2 + 2 + 4 + 4;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() >= BASE_LENGTH) {
            int beginIndex;
            //获取包头
            while (true) {
                //包头开始游标
                beginIndex = buffer.readerIndex();
                buffer.markReaderIndex();
                if (buffer.readInt() == ConstantValue.HEADER_FLAG) {
                    //找到包头，跳出循环
                    break;
                }
                //未读到包头标识不能略过4个字节，应该一个一个略过
                buffer.resetReaderIndex();
                buffer.readByte();

                //略过了1个字节，不满足了
                if (buffer.readableBytes() < BASE_LENGTH) {
                    return null;
                }
            }

            //读到包头后，就可以读取后面的数据了
            //模块号
            short module = buffer.readShort();
            //命令号
            short cmd = buffer.readShort();
            int stateCode = buffer.readInt();

            //读取数据长度
            int dataLength = buffer.readInt();
            if (dataLength < 0) {
                channel.close();
            }

            //数据包还没到齐
            if (buffer.readableBytes() < dataLength) {
                buffer.readerIndex(beginIndex);
                return null;
            }

            //数据都到齐了，组装response，将其发送给下游
            byte[] bytes = new byte[dataLength];
            buffer.readBytes(bytes);
            Response response = new Response(module, cmd, bytes);
            response.setStateCode(stateCode);
            //解析出消息对象，继续往下面的handler传递
            return response;
        }
        //数据不完整，等待完整的数据包
        return null;
    }
}
