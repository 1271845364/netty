package com.yejinhui.common.module.chat.response;

import com.yejinhui.common.module.chat.request.PrivateChatRequest;

/**
 * 聊天类型
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/3 22:18
 */
public interface ChatType {

    /**
     * 广播
     */
    byte PUBLIC_CHAT = 0;

    /**
     * 私聊
     */
    byte PRIVATE_CHAT = 1;

}
