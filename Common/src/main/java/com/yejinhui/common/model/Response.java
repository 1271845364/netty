package com.yejinhui.common.model;

import java.util.Arrays;

/**
 * 返回响应
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/28 23:09
 */
public class Response {

    /**
     * 模块
     */
    private short module;

    /**
     * 模块里面的命令号
     */
    private short cmd;

    /**
     * 状态码
     */
    private int stateCode;

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

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
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
        return "Response{" +
                "module=" + module +
                ", cmd=" + cmd +
                ", stateCode=" + stateCode +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
