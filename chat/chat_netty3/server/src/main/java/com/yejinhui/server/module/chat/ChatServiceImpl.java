package com.yejinhui.server.module.chat;

import com.yejinhui.common.core.exception.ErrorCodeException;
import com.yejinhui.common.core.model.ResultCode;
import com.yejinhui.common.core.session.SessionManager;
import com.yejinhui.common.module.ModuleId;
import com.yejinhui.common.module.chat.ChatCmd;
import com.yejinhui.common.module.chat.response.ChatResponse;
import com.yejinhui.common.module.chat.response.ChatType;
import com.yejinhui.server.module.player.dao.PlayerDao;
import com.yejinhui.server.module.player.dao.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author ye.jinhui
 * @description 聊天服务
 * @program netty-hello
 * @create 2020/3/21 13:56
 */
@Component
public class ChatServiceImpl implements ChatService {

    @Autowired
    private PlayerDao playerDao;

    @Override
    public void publicChat(long playerId, String content) {
        Player player = playerDao.getPlayerById(playerId);

        //获取所有在线玩家
        Set<Long> onlinePlayers = SessionManager.getOnlinePlayers();

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChatType(ChatType.PUBLIC_CHAT);
        chatResponse.setSendPlayerId(playerId);
        chatResponse.setSendPlayerName(player.getPlayerName());
        chatResponse.setMessage(content);

        //发送消息给每个用户
        for (Long targetPlayerId : onlinePlayers) {
            SessionManager.sendMessage(targetPlayerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
        }
    }

    @Override
    public void privateChat(long playerId, long targetPlayerId, String content) {
        //不能和自己聊天
        if (playerId == targetPlayerId) {
            throw new ErrorCodeException(ResultCode.CAN_CHAT_YOURSELF);
        }

        Player player = playerDao.getPlayerById(playerId);

        //判断目标是否存在
        Player targetPlayer = playerDao.getPlayerById(targetPlayerId);
        if (targetPlayer == null) {
            throw new ErrorCodeException(ResultCode.PLAYERNAME_NO_EXIST);
        }

        //判断对方是否在线
        if (!SessionManager.isOnlinePlayer(targetPlayerId)) {
            throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
        }

        //创建消息对象
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChatType(ChatType.PRIVATE_CHAT);
        chatResponse.setSendPlayerId(playerId);
        chatResponse.setSendPlayerName(player.getPlayerName());
        chatResponse.setTargetPlayerName(targetPlayer.getPlayerName());
        chatResponse.setMessage(content);

        //给目标对象发送消息
        SessionManager.sendMessage(targetPlayerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);

        //给自己也回一个
        SessionManager.sendMessage(playerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
    }
}
