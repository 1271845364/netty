package com.yejinhui.server.module.player.dao;

import com.yejinhui.server.module.player.dao.entity.Player;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 10:55
 */
@Component
public class PlayerDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 通过id获取玩家
     *
     * @param playerId
     * @return
     */
    public Player getPlayerById(long playerId) {
        return hibernateTemplate.get(Player.class, playerId);
    }

    /**
     * 通过玩家名字获取玩家
     *
     * @param playerName
     * @return
     */
    public Player getPlayerByName(final String playerName) {
        return hibernateTemplate.execute(new HibernateCallback<Player>() {
            @Override
            public Player doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery("select * from player where playerName = ?");
                query.setString(0, playerName);
                query.addEntity(Player.class);

                List<Player> list = query.list();
                if (list == null || list.size() == 0) {
                    return null;
                }
                return list.get(0);
            }
        });
    }

    /**
     * 创建玩家
     *
     * @param player
     * @return
     */
    public Player createPlayer(Player player) {
        long id = (long) hibernateTemplate.save(player);
        player.setPlayerId(id);
        return player;
    }
}
