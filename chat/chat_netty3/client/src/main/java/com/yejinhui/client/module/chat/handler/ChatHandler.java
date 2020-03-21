package com.yejinhui.client.module.chat.handler;

import com.yejinhui.common.core.annotation.SocketCommand;
import com.yejinhui.common.core.annotation.SocketModule;
import com.yejinhui.common.module.ModuleId;
import com.yejinhui.common.module.chat.ChatCmd;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 16:58
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {

    /**
     * 发送广播消息回包
     *
     * @param resultCode
     * @param data
     */
    @SocketCommand(command = ChatCmd.PUBLIC_CHAT)
    void publicChat(int resultCode, byte[] data);

    /**
     * 发送私人消息回包
     *
     * @param resultCode
     * @param data
     */
    @SocketCommand(command = ChatCmd.PRIVATE_CHAT)
    void privateChat(int resultCode, byte[] data);

    /**
     * 收到推送聊天信息
     *
     * @param resultCode
     * @param data
     */
    @SocketCommand(command = ChatCmd.PUSHCHAT)
    void receiveMessage(int resultCode, byte[] data);
}
