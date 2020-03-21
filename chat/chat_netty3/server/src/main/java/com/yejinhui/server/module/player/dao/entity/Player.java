package com.yejinhui.server.module.player.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ye.jinhui
 * @description 玩家实体对象
 * @program netty-hello
 * @create 2020/3/21 10:56
 */
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    private long playerId;

    private String playerName;

    private String password;

    private int level;

    /**
     * 经验
     */
    private int exp;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
