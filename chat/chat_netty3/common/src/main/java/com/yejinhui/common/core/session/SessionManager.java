package com.yejinhui.common.core.session;

import com.google.protobuf.GeneratedMessage;
import com.yejinhui.common.core.model.Response;
import com.yejinhui.common.core.serial.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 21:46
 */
public class SessionManager {

    /**
     * 在线会话
     */
    private static final ConcurrentHashMap<Long, Session> onlineSessions = new ConcurrentHashMap<>(16);

    /**
     * 加入
     *
     * @param playerId
     * @param session
     * @return
     */
    public static boolean putSession(long playerId, Session session) {
        if (!onlineSessions.containsKey(playerId)) {
            return onlineSessions.putIfAbsent(playerId, session) == null ? true : false;
        }
        return false;
    }

    /**
     * 移除
     *
     * @param playerId
     * @return
     */
    public static Session removeSession(long playerId) {
        return onlineSessions.remove(playerId);
    }

    /**
     * 发送消息【自定义协议】
     *
     * @param playerId
     * @param module
     * @param cmd
     * @param message
     * @param <T>
     */
    public static <T extends Serializer> void sendMessage(long playerId, short module, short cmd, T message) {
        Session session = onlineSessions.get(playerId);
        if (session != null && session.isConnected()) {
            Response response = new Response(module, cmd, message.getBytes());
            session.write(response);
        }
    }

    /**
     * 发送消息【protoBuf协议】
     *
     * @param playerId
     * @param module
     * @param cmd
     * @param message
     * @param <T>
     */
    public static <T extends GeneratedMessage> void sendMessage(long playerId, short module, short cmd, T message) {
        Session session = onlineSessions.get(playerId);
        if (session != null && session.isConnected()) {
            Response response = new Response(module, cmd, message.toByteArray());
            session.write(response);
        }
    }

    /**
     * 是否在线
     *
     * @param playerId
     * @return
     */
    public static boolean isOnlinePlayer(long playerId) {
        return onlineSessions.containsKey(playerId);
    }

    /**
     * 获取所有在线玩家
     *
     * @return
     */
    public static Set<Long> getOnlinePlayers() {
        //Collections.unmodifiableSet是将set不可以修改，是只读的
        return Collections.unmodifiableSet(onlineSessions.keySet());
    }

}
