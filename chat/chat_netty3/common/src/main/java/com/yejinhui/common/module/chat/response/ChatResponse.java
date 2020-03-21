package com.yejinhui.common.module.chat.response;

import com.yejinhui.common.core.serial.Serializer;

/**
 * 聊天响应
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/3 22:16
 */
public class ChatResponse extends Serializer {

    /**
     * 发送者id
     */
    private long sendPlayerId;

    /**
     * 玩家名
     */
    private String sendPlayerName;

    /**
     * 目标玩家
     */
    private String targetPlayerName;

    /**
     * 消息类型
     * 0 广播消息
     * 1 私聊
     */
    private byte ChatType;

    /**
     * 消息
     */
    private String message;

    public long getSendPlayerId() {
        return sendPlayerId;
    }

    public void setSendPlayerId(long sendPlayerId) {
        this.sendPlayerId = sendPlayerId;
    }

    public String getSendPlayerName() {
        return sendPlayerName;
    }

    public void setSendPlayerName(String sendPlayerName) {
        this.sendPlayerName = sendPlayerName;
    }

    public String getTargetPlayerName() {
        return targetPlayerName;
    }

    public void setTargetPlayerName(String targetPlayerName) {
        this.targetPlayerName = targetPlayerName;
    }

    public byte getChatType() {
        return ChatType;
    }

    public void setChatType(byte chatType) {
        ChatType = chatType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "sendPlayerId=" + sendPlayerId +
                ", sendPlayerName='" + sendPlayerName + '\'' +
                ", targetPlayerName='" + targetPlayerName + '\'' +
                ", ChatType=" + ChatType +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    protected void read() {
        this.sendPlayerId = readLong();
        this.sendPlayerName = readString();
        this.targetPlayerName = readString();
        this.ChatType = readByte();
        this.message = readString();
    }

    @Override
    protected void write() {
        writeLong(this.sendPlayerId);
        writeString(this.sendPlayerName);
        writeString(this.targetPlayerName);
        writeByte(this.ChatType);
        writeString(this.message);
    }

}
