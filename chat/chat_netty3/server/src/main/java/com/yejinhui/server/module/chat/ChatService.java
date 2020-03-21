package com.yejinhui.server.module.chat;

/**
 * 聊天服务
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 13:55
 */
public interface ChatService {

    /**
     * 群发消息
     *
     * @param playerId
     * @param content
     */
    void publicChat(long playerId, String content);

    /**
     * 私聊
     *
     * @param playerId
     * @param targetPlayerId
     * @param content
     */
    void privateChat(long playerId, long targetPlayerId, String content);
}
