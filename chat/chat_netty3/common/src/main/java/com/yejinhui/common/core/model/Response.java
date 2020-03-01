package com.yejinhui.common.core.model;

import java.util.Arrays;

/**
 * 响应对象
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 16:57
 */
public class Response {

    /**
     * 模块号
     */
    private short module;

    /**
     * 命令号
     */
    private short cmd;

    /**
     * 结果码
     */
    private int stateCode = ResultCode.SUCCESS;

    /**
     * 数据
     */
    private byte[] data;

    public Response() {
    }

    public Response(Request request) {
        this.module = request.getModule();
        this.cmd = request.getCmd();
    }

    public Response(short module, short cmd, byte[] data) {
        this.module = module;
        this.cmd = cmd;
        this.data = data;
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
}
