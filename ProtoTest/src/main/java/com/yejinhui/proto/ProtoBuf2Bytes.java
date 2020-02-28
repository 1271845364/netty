package com.yejinhui.proto;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Arrays;

/**
 * protobuf学习
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 17:08
 */
public class ProtoBuf2Bytes {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        byte[] bytes = toBytes();
        toPLayer(bytes);
    }

    /**
     * 序列化
     */
    public static byte[] toBytes() {
        //获取PBPlayer的构造器
        PlayerModule.PBPlayer.Builder builder = PlayerModule.PBPlayer.newBuilder();

        //设置数据
        builder.setPlayerId(101).setAge(20).setName("peter").addSkills(1001);

        //构造出对象
        PlayerModule.PBPlayer pbPlayer = builder.build();

        //对象序列化成字节数组
        byte[] byteArray = pbPlayer.toByteArray();

        System.out.println(Arrays.toString(byteArray));

        return byteArray;
    }

    /**
     * 反序列化
     *
     * @param bytes
     */
    public static void toPLayer(byte[] bytes) throws InvalidProtocolBufferException {
        PlayerModule.PBPlayer pbPlayer = PlayerModule.PBPlayer.parseFrom(bytes);
        System.out.println(pbPlayer);
    }

}
