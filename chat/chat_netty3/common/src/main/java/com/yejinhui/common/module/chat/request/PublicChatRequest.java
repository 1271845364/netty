package com.yejinhui.common.module.chat.request;

import com.yejinhui.common.core.serial.Serializer;

/**
 * 广播请求
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/3 22:15
 */
public class PublicChatRequest extends Serializer {

    /**
     * 内容
     */
    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "PublicChatRequest{" +
                "context='" + context + '\'' +
                '}';
    }

    @Override
    protected void read() {
        this.context = readString();
    }

    @Override
    protected void write() {
        writeString(this.context);
    }
}
