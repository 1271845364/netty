package com.yejinhui.common.codec;

import com.yejinhui.common.constant.ConstantValue;
import com.yejinhui.common.model.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 请求解码
 * <p>
 * 包头4个字节
 * 模块号2个字节
 * 命令号2个字节
 * 数据长度4个字节
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/28 22:43
 */
public class RequestDecoder extends FrameDecoder {

    /**
     * 数据包基本长度
     * <p>
     * 包头   4
     * 模块号 2
     * 命令号 2
     * 数据长度 4
     */
    private static int BASE_LENGTH = 4 + 2 + 2 + 4;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        //可读长度必须大于基本长度
        if (buffer.readableBytes() > BASE_LENGTH) {

            //防止socket字节流攻击 = 篡改了数据长度，改的Integer.MAX_VALUE，内存会直接撑爆了
            if (buffer.readableBytes() > 2048) {
                buffer.skipBytes(buffer.readableBytes());
            }

            //记录包头开始的位置index = 读指针
            int beginReader;
            while (true) {
                //每次标记下，记录包头开始的位置
                beginReader = buffer.readerIndex();
                if (buffer.readInt() == ConstantValue.FLAG) {
                    //读到了包头就跳出来，继续读取后面的数据，只有读到了包头，才算开始位置
                    break;
                }
                //上面的readInt()是读了4个字节，一次略过4个字节，有可能跳过了包头的一个请求的位置，
                // 所以应该略过1个字节，一个一个略过就不会遗漏
                buffer.resetReaderIndex();
                buffer.readByte();

                //长度又变的不满足了
                if (buffer.readableBytes() < BASE_LENGTH) {
                    //等待后面的数据包的到来
                    return null;
                }
            }

            short module = buffer.readShort();
            short cmd = buffer.readShort();
            //数据长度
            int length = buffer.readInt();
            if (buffer.readableBytes() < length) {
                //重置为原来的位置，用于记录，不然读到的是不完整的数据包
                buffer.readerIndex(beginReader);
                //数据包不完整，等待后面的数据的到来
                return null;
            }

            //数据都已经到了，读取data并封装成request对象
            byte[] dst = new byte[length];
            buffer.readBytes(dst);
            Request request = new Request();
            request.setModule(module);
            request.setCmd(cmd);
            request.setData(dst);
            //解析出来的请求对象继续往后面传递 = 通过sendUpStreamEvent
            return request;
        }
        //数据包不完整，需要等待后面的包来
        return null;
    }
}
