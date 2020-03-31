package com.yejinhui.common.model;

import org.jboss.netty.buffer.ChannelBuffer;

import java.nio.channels.Channel;
import java.util.Arrays;

/**
 * 请求对象
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/28 22:13
 */
public class Request {

    /**
     * 请求模块
     */
    private short module;

    /**
     * getDataLength
     */
    private short cmd;

    /**
     * 数据
     */
    private byte[] data;

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDataLength() {
        if (data == null) {
            return 0;
        }
        return data.length;
    }

    @Override
    public String toString() {
        return "Request{" +
                "module=" + module +
                ", cmd=" + cmd +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
