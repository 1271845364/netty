package com.yejinhui.common.codec;

import com.yejinhui.common.constant.ConstantValue;
import com.yejinhui.common.model.Request;
import com.yejinhui.common.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 响应解码
 * <p>
 * 包头4个字节
 * 模块号2个字节
 * 命令号2个字节
 * 状态码4个字节
 * 数据长度4个字节
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/28 22:43
 */
public class ResponseDecoder extends FrameDecoder {

    /**
     * 数据包基本长度
     * <p>
     * 包头   4
     * 模块号 2
     * 命令号 2
     * 状态码 4
     * 数据长度 4
     */
    private static int BASE_LENGTH = 4 + 2 + 2 + 4;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        //可读长度必须大于基本长度
        if (buffer.readableBytes() > BASE_LENGTH) {

            //记录包头开始的位置index = 读指针
            int beginReader = buffer.readerIndex();
            while (true) {
                if (buffer.readInt() == ConstantValue.FLAG) {
                    //读到了包头就跳出来，继续读取后面的
                    break;
                }
            }

            short module = buffer.readShort();
            short cmd = buffer.readShort();
            int stateCode = buffer.readInt();
            //数据长度
            int length = buffer.readInt();
            //判断请求数据包数据是否到齐了
            if (buffer.readableBytes() < length) {
                //重置为原来的位置，用于记录，不然读到的是不完整的数据包
                buffer.readerIndex(beginReader);
                //数据包不完整，等待后面的数据的到来
                return null;
            }

            //数据都已经到了，读取data并封装成response对象
            byte[] dst = new byte[length];
            buffer.readBytes(dst);
            Response response = new Response();
            response.setModule(module);
            response.setCmd(cmd);
            response.setStateCode(stateCode);
            response.setData(dst);
            //解析出来的请求对象继续往后面传递 = 通过sendUpStreamEvent
            return response;
        }
        //数据包不完整，需要等待后面的包来
        return null;
    }
}
