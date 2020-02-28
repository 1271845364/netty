package com.yejinhui.serial;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 21:36
 */
public class Test2 {

    public static void main(String[] args) {
        int id = 4444;
        int age = 21;

        //容量在初始化的时候就确定大小了，放数据的时候超过了这个容量就会报错
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putInt(id);
        byteBuffer.putInt(age);
        byte[] array = byteBuffer.array();
        System.out.println(Arrays.toString(array));

        ByteBuffer byteBuffer1 = ByteBuffer.wrap(array);
        System.out.println("id:" + byteBuffer1.getInt());
        System.out.println("age:" + byteBuffer1.getInt());
    }
}
