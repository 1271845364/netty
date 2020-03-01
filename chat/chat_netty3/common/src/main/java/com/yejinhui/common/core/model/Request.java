package com.yejinhui.common.core.model;

import java.util.Arrays;

/**
 * 请求对象
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 16:05
 */
public class Request {

    /**
     * 模块号
     */
    private short module;

    /**
     * 命令号
     */
    private short cmd;

    /**
     * 数据
     */
    private byte[] data;

    public static Request valueOf(short module, short cmd, byte[] data) {
        Request request = new Request();
        request.setModule(module);
        request.setCmd(cmd);
        request.setData(data);
        return request;
    }

    @Override
    public String toString() {
        return "Request{" +
                "module=" + module +
                ", cmd=" + cmd +
                ", data=" + Arrays.toString(data) +
                '}';
    }

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
}
