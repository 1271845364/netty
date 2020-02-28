package com.yejinhui.java;

import java.io.*;
import java.util.Arrays;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 17:54
 */
public class JAVA2Bytes {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        byte[] bytes = toBytes();
        System.out.println(Arrays.toString(bytes));
        toPLayer(bytes);
    }

    /**
     * 序列化
     */
    public static byte[] toBytes() throws IOException {
        //Java对象序列化
        Player player = new Player();
        player.setAge(20);
        player.setName("peter");
        player.setPlayerId(101);
        player.setSkills(Arrays.asList(1001, 1002));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(player);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param bytes
     */
    public static void toPLayer(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        //1M大小
        byte[] buf = new byte[1024 * 1024];
        Player player = (Player) objectInputStream.readObject();
        System.out.println(player);
    }

}
