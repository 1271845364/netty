package com.yejinhui.serial;

import com.yejinhui.serial.core.Serializer;

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

    private List<Integer> skills = new ArrayList<>();

    private Resource resource = new Resource();

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
        this.skills = readList(Integer.class);
        this.resource = read(Resource.class);
    }

    /**
     * 序列化
     */
    @Override
    protected void write() {
        writeLong(playerId);
        writeInt(age);
        writeList(skills);
        writeObject(resource);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", age=" + age +
                ", skills=" + skills +
                ", resource=" + resource +
                '}';
    }
}
