package com.yejinhui.common.module.chat.request;

import com.yejinhui.common.core.serial.Serializer;

/**
 * 私聊请求
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/3 22:05
 */
public class PrivateChatRequest extends Serializer {

    /**
     * 私聊用户id
     */
    private long targetPlayerId;

    /**
     * 内容
     */
    private String context;

    public long getTargetPlayerId() {
        return targetPlayerId;
    }

    public void setTargetPlayerId(long targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    protected void read() {
        this.targetPlayerId = readLong();
        this.context = readString();
    }

    @Override
    protected void write() {
        writeLong(this.targetPlayerId);
        writeString(this.context);
    }

    @Override
    public String toString() {
        return "PrivateChatRequest{" +
                "targetPlayerId=" + targetPlayerId +
                ", context='" + context + '\'' +
                '}';
    }
}
