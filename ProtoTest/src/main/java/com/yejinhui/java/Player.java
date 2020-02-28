package com.yejinhui.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 17:52
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -1517259130596411387L;
    private long playerId;

    private int age;

    private String name;

    private List<Integer> skills = new ArrayList<>(16);

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }
}
