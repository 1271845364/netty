package com.yejinhui.common.module.chat;

/**
 * 聊天模块
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 22:38
 */
public interface ChatCmd {

    /**
     * 广播聊天
     */
    short PUBLIC_CHAT = 1;

    /**
     * 私人消息
     */
    short PRIVATE_CHAT = 2;

    //============================================================================

    /**
     * 消息推送
     */
    short PUSHCHAT = 101;

}
