package com.yejinhui.serial;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/25 22:15
 */
public class Player extends Serializer {

    private long playerId;

    private int age;

    private String name;

    private List<Integer> skills = new ArrayList<>();

    public Player() {
    }

    public Player(long playerId, int age, String name) {
        this.playerId = playerId;
        this.age = age;
        this.name = name;
    }

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

    /**
     * 反序列化
     */
    @Override
    protected void read() {
        this.playerId = readLong();
        this.age = readInt();
        this.name = readString();
        this.skills = readList(Integer.class);
    }

    /**
     * 序列化
     */
    @Override
    protected void write() {
        writeLong(playerId);
        writeInt(age);
        writeString(name);
        writeList(skills);
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
