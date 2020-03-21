package com.yejinhui.server.module.player.service;

import com.yejinhui.common.core.exception.ErrorCodeException;
import com.yejinhui.common.core.model.Response;
import com.yejinhui.common.core.model.ResultCode;
import com.yejinhui.common.core.session.Session;
import com.yejinhui.common.core.session.SessionManager;
import com.yejinhui.common.module.player.response.PlayerResponse;
import com.yejinhui.server.module.player.dao.PlayerDao;
import com.yejinhui.server.module.player.dao.entity.Player;
import org.apache.log4j.spi.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ye.jinhui
 * @description 玩家服务
 * @program netty-hello
 * @create 2020/3/21 12:31
 */
@Component
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao playerDao;

    @Override
    public PlayerResponse registerAndLogin(Session session, String playerName, String password) {
        Player existPlayer = playerDao.getPlayerByName(playerName);
        if (existPlayer != null) {
            throw new ErrorCodeException(ResultCode.PLAYERNAME_EXIST);
        }

        //创建账号
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setPassword(password);
        playerDao.createPlayer(player);
        return login(session, playerName, password);
    }

    @Override
    public PlayerResponse login(Session session, String playerName, String password) {
        //判断当前会话是否登录
        if (session.getAttachment() != null) {
            throw new ErrorCodeException(ResultCode.HAS_LOGIN);
        }

        //判断玩家是否存在
        Player player = playerDao.getPlayerByName(playerName);
        if (player == null) {
            throw new ErrorCodeException(ResultCode.PLAYERNAME_NO_EXIST);
        }

        //密码错误
        if (!player.getPassword().equals(password)) {
            throw new ErrorCodeException(ResultCode.PASSWORD_ERROR);
        }

        //判断是否在其他地方登录过
        if (SessionManager.isOnlinePlayer(player.getPlayerId())) {
            //剔除下线
            Session oldSession = SessionManager.removeSession(player.getPlayerId());
            oldSession.removeAttachment();
            oldSession.close();
        }

        //加入在线玩家会话
        SessionManager.putSession(player.getPlayerId(),session);

        //返回playerResponse对象
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerId(player.getPlayerId());
        playerResponse.setPlayerName(playerName);
        playerResponse.setExp(player.getExp());
        playerResponse.setLevel(player.getLevel());
        return playerResponse;
    }
}
