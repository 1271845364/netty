package com.yejinhui.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 20:57
 */
public class Test1 {

    public static void main(String[] args) throws IOException {
        int id = 101;
        int age = 21;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(int2bytes(id));
        byteArrayOutputStream.write(int2bytes(age));
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        byte[] idBytes = new byte[4];
        byteArrayInputStream.read(idBytes);
        System.out.println("id:" + bytes2int(idBytes));

        byte[] ageBytes = new byte[4];
        byteArrayInputStream.read(ageBytes);
        System.out.println("age:" + bytes2int(ageBytes));
    }

    /**
     * 大端字节序列(先写高位，再写低位)
     *
     * @param i
     * @return
     */
    public static byte[] int2bytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >> 3 * 8);
        bytes[1] = (byte) (i >> 2 * 8);
        bytes[2] = (byte) (i >> 1 * 8);
        bytes[3] = (byte) (i >> 0 * 8);
        return bytes;
    }

    /**
     * 大端字节序列(先写高位，再写低位)
     *
     * @param bytes
     * @return
     */
    public static int bytes2int(byte[] bytes) {
        return (bytes[0] << 3 * 8) |
                (bytes[1] << 2 * 8) |
                (bytes[2] << 1 * 8) |
                (bytes[3] << 0 * 8);
    }

}
